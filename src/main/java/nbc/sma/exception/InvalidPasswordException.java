package nbc.sma.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BizException {

    public InvalidPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid password");
    }
}
