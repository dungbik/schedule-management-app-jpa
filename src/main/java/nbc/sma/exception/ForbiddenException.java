package nbc.sma.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BizException {

    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
