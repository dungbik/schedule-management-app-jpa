package nbc.sma.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends BizException {

    public AuthenticationException() {
        super(HttpStatus.UNAUTHORIZED, "Please log in and try again.");
    }
}