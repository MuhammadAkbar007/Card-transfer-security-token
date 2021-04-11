package pdp.uz.cardtransferwithoutdbsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Card;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Income;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Outcome;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.payload.OutcomeDto;
import pdp.uz.cardtransferwithoutdbsecurity.repository.CardRepository;
import pdp.uz.cardtransferwithoutdbsecurity.repository.IncomeRepository;
import pdp.uz.cardtransferwithoutdbsecurity.repository.OutcomeRepository;
import pdp.uz.cardtransferwithoutdbsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OutcomeService {

    @Autowired
    OutcomeRepository outcomeRepository;

    @Autowired
    IncomeRepository incomeRepository;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    CardRepository cardRepository;

    public ApiResponse add(OutcomeDto outcomeDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        Optional<Card> optionalCardTo = cardRepository.findById(outcomeDto.getToCardId());
        if (!optionalCardTo.isPresent()) return new ApiResponse("To card not found !", false);
        Card cardTo = optionalCardTo.get();

        Optional<Card> optionalCardFrom = cardRepository.findById(outcomeDto.getFromCardId());
        if (!optionalCardFrom.isPresent()) return new ApiResponse("From card not found !", false);
        Card cardFrom = optionalCardFrom.get();

        if (!userName.equals(cardFrom.getUsername()))
            return new ApiResponse("This card is not yours !", false);

        double balance = cardFrom.getBalance();
        double totalAmount = outcomeDto.getAmount() + (outcomeDto.getAmount() / 100 * outcomeDto.getCommissionPercent());
        if (balance < totalAmount) return new ApiResponse("Balance is not sufficient", false);

        Outcome outcome = new Outcome();
        outcome.setAmount(outcomeDto.getAmount());
        outcome.setCommissionPercent(outcomeDto.getCommissionPercent());
        outcome.setFromCard(cardFrom);
        outcome.setToCard(cardTo);
        outcome.setDate(new Date());
        outcomeRepository.save(outcome);

        Income income = new Income();
        income.setDate(new Date());
        income.setFromCard(cardFrom);
        income.setToCard(cardTo);
        incomeRepository.save(income);

        cardFrom.setBalance(balance - totalAmount);
        cardTo.setBalance(cardTo.getBalance() + outcomeDto.getAmount());
        cardRepository.save(cardFrom);
        cardRepository.save(cardTo);

        return new ApiResponse("Transaction successfully done !", true);
    }

    public List<Outcome> getAll(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        List<Outcome> outcomeList = new ArrayList<>();

        for (Outcome outcome : outcomeRepository.findAll()) {
            if (outcome.getFromCard().getUsername().equals(userName))
                outcomeList.add(outcome);
        }
        return outcomeList;
    }

    public ApiResponse getOne(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        Optional<Outcome> optionalOutcome = outcomeRepository.findById(id);
        if (!optionalOutcome.isPresent()) return new ApiResponse("No", false, "Outcome by this id not found !");
        if (!userName.equals(optionalOutcome.get().getFromCard().getUsername()))
            return new ApiResponse("No", false, "This outcome is not yours !");
        return new ApiResponse("Ok", true, optionalOutcome.get());
    }
}
