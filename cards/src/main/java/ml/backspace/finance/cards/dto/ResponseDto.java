package ml.backspace.finance.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Schema(name = "API Response", description = "Schema to hold the successful API transactions")
@Data
@AllArgsConstructor
public class ResponseDto {
    @Schema(description = "Status code in the response")
    private String statusCode;

    @Schema(description = "Status message in the response")
    private String statusMsg;
}
