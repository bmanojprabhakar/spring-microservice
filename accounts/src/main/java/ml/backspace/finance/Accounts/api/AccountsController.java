package ml.backspace.finance.Accounts.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import ml.backspace.finance.Accounts.constants.AccountsConstants;
import ml.backspace.finance.Accounts.dto.ApiResponseDto;
import ml.backspace.finance.Accounts.dto.ContactInfo;
import ml.backspace.finance.Accounts.dto.CustomerDto;
import ml.backspace.finance.Accounts.dto.ErrorResponse;
import ml.backspace.finance.Accounts.service.AccountsService;
import org.springframework.beans.factory.annotation.Value;
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

import java.util.List;
import java.util.Map;

@Tag(
        name = "CRUD REST APIs for Accounts",
        description = "CRUD REST APIs to CREATE, UPDATE, FETCH AND DELETE account details"
)
@RestController
@RequestMapping(path = "api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {
    private final AccountsService accountsService;

    private final ContactInfo contactInfo;

    public AccountsController(AccountsService accountsService, ContactInfo contactInfo) {
        this.accountsService = accountsService;
        this.contactInfo = contactInfo;
    }

    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new Customer & Account"
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
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {
        accountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch Account Details REST API",
            description = "REST API to fetch Customer &  Account details based on a mobile number"
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
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        CustomerDto customerDto = accountsService.fetchAccountDetails(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @Operation(
            summary = "Update Account Details REST API",
            description = "REST API to update Customer &  Account details based on a account number"
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
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto> updateAccounts(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = accountsService.updateCustomer(customerDto);

        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ApiResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417));
    }

    @Operation(
            summary = "Delete Account & Customer Details REST API",
            description = "REST API to delete Customer &  Account details based on a mobile number"
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
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto> deleteAccounts(@RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
        boolean isDeleted = accountsService.deleteCustomer(mobileNumber);

        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ApiResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        }
        return ResponseEntity
                .status(HttpStatus.EXPECTATION_FAILED)
                .body(new ApiResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417));
    }

    @Operation(
            summary = "Contact Info API",
            description = "To obtain the SPOC for this service"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Operation success"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    }
    )
    @GetMapping("/contact-info")
    public ResponseEntity<ContactInfo> whomToContact() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(contactInfo);
    }
}
