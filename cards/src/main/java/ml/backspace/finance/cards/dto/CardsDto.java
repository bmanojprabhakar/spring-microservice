package ml.backspace.finance.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Cards", description = "Schema to hold all card related data")
@Data @AllArgsConstructor @NoArgsConstructor
public class CardsDto {
    @Schema(description = "Mobile number of customer", example = "8877996655")
    @NotEmpty(message = "Mobile Number can not be a null or empty")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile Number must be 10 digits")
    private String mobileNumber;

    @Schema(description = "Card number of customer", example = "7683698462345098")
    @NotEmpty(message = "Card number cannot be null or empty")
    @Pattern(regexp="(^$|[0-9]{16})",message = "Card Number must be 16 digits")
    private String cardNumber;

    @Schema(description = "Type of the card", example = "Credit Card")
    @NotEmpty(message = "CardType can not be a null or empty")
    private String cardType;

    @Schema(description = "Total limit of the card", example = "10000")
    @Positive(message = "Total card limit should be greater than zero")
    private Integer totalLimit;

    @Schema(description = "Amount utilized of the card", example = "1000")
    @PositiveOrZero(message = "Amount used should be zero or greater than zero")
    private Integer amountUsed;

    @Schema(description = "Balance available credit on the card", example = "9000")
    @PositiveOrZero(message = "Available amount should be zero or greater than zero")
    private Integer availableAmount;
}
