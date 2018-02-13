package somun.exception;

import org.springframework.http.HttpStatus;

public  class APIServerException extends APIException {
    private static final long serialVersionUID = -4392805963590888231L;

    public APIServerException() {
        super();
    }

    public APIServerException(String message) {
        super(message);
    }

    public APIServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIServerException(Throwable cause) {
        super(cause);
    }

    public APIServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Override
    public Integer getCode() {
        return 20000;
    }
}
