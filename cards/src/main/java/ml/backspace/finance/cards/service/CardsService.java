package ml.backspace.finance.cards.service;

import ml.backspace.finance.cards.dto.CardsDto;
import ml.backspace.finance.cards.entity.Cards;

import java.util.Optional;

public interface CardsService {
    void createCard(String mobileNumber);

    CardsDto fetchCardDetails(String mobileNumber);

    boolean updateCard(CardsDto cardsDto);

    boolean delete(String mobileNumber);
}
