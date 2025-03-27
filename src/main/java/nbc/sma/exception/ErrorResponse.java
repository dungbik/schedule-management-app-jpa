package nbc.sma.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object detail;

    public static ErrorResponse of(HttpStatus httpStatus, String message, String path) {
        return new ErrorResponse(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), message, path, null);
    }

    public static ErrorResponse of(HttpStatus httpStatus, String message, String path, Object detail) {
        return new ErrorResponse(LocalDateTime.now(), httpStatus.value(), httpStatus.name(), message, path, detail);
    }
}
