package pdp.uz.cardtransferwithoutdbsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pdp.uz.cardtransferwithoutdbsecurity.entity.Card;
import pdp.uz.cardtransferwithoutdbsecurity.payload.ApiResponse;
import pdp.uz.cardtransferwithoutdbsecurity.payload.CardDto;
import pdp.uz.cardtransferwithoutdbsecurity.repository.CardRepository;
import pdp.uz.cardtransferwithoutdbsecurity.security.JwtProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServise {

    @Autowired
    CardRepository cardRepository;

    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse add(CardDto cardDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);

        Card card = new Card();
        card.setUsername(jwtProvider.getUsernameFromToken(token));
        card.setCardNumber(cardDto.getCardNumber());
        card.setExpiredDate(cardDto.getExpiredDate());

        cardRepository.save(card);
        return new ApiResponse("New card successfully added !", true);
    }

    public List<Card> getCards(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        List<Card> userCards = new ArrayList<>();
        for (Card card : cardRepository.findAll()) {
            if (card.getUsername().equals(userName))
                userCards.add(card);
        }
        return userCards;
    }

    public Card getOne(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return null;
        if (!userName.equals(optionalCard.get().getUsername())) return null;
        return optionalCard.orElse(null);
    }

    public ApiResponse edit(Integer id, CardDto cardDto, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        Optional<Card> optionalCard = cardRepository.findById(id);
        if (!optionalCard.isPresent()) return new ApiResponse("Card by this id not found !", false);
        Card card = optionalCard.get();
        if (!card.getUsername().equals(userName)) return new ApiResponse("This card is not yours !", false);
        card.setCardNumber(cardDto.getCardNumber());
        card.setExpiredDate(cardDto.getExpiredDate());
        cardRepository.save(card);
        return new ApiResponse("Card successfully edited !", true);
    }

    public ApiResponse delete(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        token = token.substring(7);
        String userName = jwtProvider.getUsernameFromToken(token);

        try {
            Optional<Card> optionalCard = cardRepository.findById(id);
            if (!optionalCard.isPresent()) return new ApiResponse("Card by this id not found !", false);
            if (!userName.equals(optionalCard.get().getUsername()))
                return new ApiResponse("This card is not yours !", false);
            cardRepository.deleteById(id);
            return new ApiResponse("Card successfully deleted !", true);
        } catch (Exception e) {
            return new ApiResponse("Error while deleting !", false);
        }
    }
}
