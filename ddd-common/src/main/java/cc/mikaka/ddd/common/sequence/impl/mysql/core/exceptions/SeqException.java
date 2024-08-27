package cc.mikaka.ddd.common.sequence.impl.mysql.core.exceptions;

public class SeqException extends RuntimeException {

    public SeqException(String message) {
        super(message);
    }

    public SeqException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeqException(Throwable cause) {
        super(cause);
    }

}
