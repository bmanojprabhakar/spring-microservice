package ml.backspace.finance.cards.service.impl;

import lombok.AllArgsConstructor;
import ml.backspace.finance.cards.constants.CardsConstants;
import ml.backspace.finance.cards.dto.CardsDto;
import ml.backspace.finance.cards.entity.Cards;
import ml.backspace.finance.cards.exception.CardAlreadyExistsException;
import ml.backspace.finance.cards.exception.ResourceNotFoundException;
import ml.backspace.finance.cards.mapper.CardsMapper;
import ml.backspace.finance.cards.repository.CardsRepository;
import ml.backspace.finance.cards.service.CardsService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements CardsService {
    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> cards = cardsRepository.findByMobileNumber(mobileNumber);

        if(cards.isPresent()) {
            throw new CardAlreadyExistsException("Card Already exists for the mobile number "+mobileNumber);
        }

        Cards newCard = new Cards();
        long randomCardNumber = 1000000000000000L + new Random().nextLong(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        cardsRepository.save(newCard);
    }

    @Override
    public CardsDto fetchCardDetails(String mobileNumber) {
        Cards cards = cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile Number", mobileNumber));
        return CardsMapper.mapToCardsDto(cards, new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards existingCard = cardsRepository.findByMobileNumber(cardsDto.getMobileNumber())
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile Number", cardsDto.getMobileNumber()));
        Cards updatedCard = CardsMapper.mapToCards(cardsDto, existingCard);
        cardsRepository.save(updatedCard);
        return true;
    }

    @Override
    public boolean delete(String mobileNumber) {
        Cards existingCard = cardsRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile Number", mobileNumber));
        cardsRepository.deleteById(existingCard.getCardId());
        return true;
    }
}
