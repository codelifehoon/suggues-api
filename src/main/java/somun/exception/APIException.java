package somun.exception;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.HttpStatus;

public abstract class APIException extends RuntimeException {
    private static final long serialVersionUID = -140144073760962802L;

    public APIException() {
        super();
    }

    public APIException(String message) {
        super(message);
    }

    public APIException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIException(Throwable cause) {
        super(cause);
    }

    public APIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public abstract Integer getCode();

    public abstract HttpStatus getStatus();

    public String getMessageCode() {
        return getClass().getSimpleName();
    }

    public Object[] getMessageArguments() {
        return ArrayUtils.toArray();
    }
}
