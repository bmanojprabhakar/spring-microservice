package ml.backspace.finance.loan.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import ml.backspace.finance.loan.constants.LoansConstants;
import ml.backspace.finance.loan.dto.ErrorResponseDto;
import ml.backspace.finance.loan.dto.LoansContactInfoDto;
import ml.backspace.finance.loan.dto.LoansDto;
import ml.backspace.finance.loan.dto.ResponseDto;
import ml.backspace.finance.loan.service.LoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@Tag(
        name = "CRUD REST APIs for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE loan details"
)
@RestController
@RequestMapping("/api/loans/v1")
@Validated
public class LoansController {
    private LoansService loansService;

    public LoansController(LoansService loansService) {
        this.loansService = loansService;
    }

    @Autowired
    private LoansContactInfoDto loansContactInfoDto;

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API to create new loan account"
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
    @PostMapping("/createLoan")
    public ResponseEntity<ResponseDto> create(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        loansService.createLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Loan Details REST API",
            description = "REST API to fetch loan details based on a mobile number"
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
    }
    )
    @GetMapping("/fetchLoans")
    public ResponseEntity<LoansDto> fetchLoans(@Valid @RequestParam
                                                   @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        LoansDto loansDto = loansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(loansDto);
    }

    @Operation(
            summary = "Update Loan Details REST API",
            description = "REST API to update loan details based on a loan number"
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
    }
    )
    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody LoansDto loansDto) {
        boolean isUpdated = loansService.updateLoan(loansDto);
        if(isUpdated) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
    }

    @Operation(
            summary = "Delete Loan Details REST API",
            description = "REST API to delete Loan details based on a mobile number"
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
    }
    )
    @DeleteMapping("/deleteLoan")
    public ResponseEntity<ResponseDto> delete(@Valid @RequestParam
                                                  @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = loansService.deleteLoan(mobileNumber);
        if(isDeleted) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
        }
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
    }

    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(loansContactInfoDto);
    }
}
