package ml.backspace.finance.cards.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, String data) {
        super(String.format("%s not found with %s for the provided data %s", resourceName, fieldName, data));
    }
}
