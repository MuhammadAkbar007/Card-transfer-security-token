package pdp.uz.cardtransferwithoutdbsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Income;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.repository.IncomeRepository;
import pdp.uz.cardtransferwithoutdbsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    IncomeRepository incomeRepository;

    public List<Income> getAll(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        List<Income> incomeList = new ArrayList<>();

        for (Income income : incomeRepository.findAll()) {
            if (income.getToCard().getUsername().equals(userName))
                incomeList.add(income);
        }
        return incomeList;
    }

    public ApiResponse getOne(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (!optionalIncome.isPresent()) return new ApiResponse("Not found", false, "Income by this id not found !");
        if (!userName.equals(optionalIncome.get().getToCard().getUsername()))
            return new ApiResponse("NO", false, "This card is not yours !");
        return new ApiResponse("Yes", true, optionalIncome.get());
    }
}
