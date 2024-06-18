package ml.backspace.finance.cards.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import ml.backspace.finance.cards.constants.CardsConstants;
import ml.backspace.finance.cards.dto.CardsContactInfoDto;
import ml.backspace.finance.cards.dto.CardsDto;
import ml.backspace.finance.cards.dto.ErrorResponseDto;
import ml.backspace.finance.cards.dto.ResponseDto;
import ml.backspace.finance.cards.service.CardsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cards/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CardsController {
    private final CardsService cardsService;
    private final CardsContactInfoDto contactInfo;

    public CardsController(CardsService cardsService, CardsContactInfoDto contactInfo) {
        this.cardsService = cardsService;
        this.contactInfo = contactInfo;
    }

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCards(@Valid @RequestParam
                                                       @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                               String mobileNumber) {
        cardsService.createCard(mobileNumber);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstants.STATUS_201, CardsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Card Details REST API",
            description = "REST API to fetch card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetchCards")
    public ResponseEntity<CardsDto> fetchCardDetails(@Valid @RequestParam
                                                         @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                         String mobileNumber) {
        CardsDto cardsDto = cardsService.fetchCardDetails(mobileNumber);
        return new ResponseEntity<>(cardsDto, HttpStatus.OK);
    }

    @Operation(
            summary = "Update Card Details REST API",
            description = "REST API to update card details based on a card number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = cardsService.updateCard(cardsDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE));

    }

    @Operation(
            summary = "Delete Card Details REST API",
            description = "REST API to delete Card details based on a mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isUpdated = cardsService.delete(mobileNumber);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200));
        }

        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> contactInfo() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactInfo);
    }
}
