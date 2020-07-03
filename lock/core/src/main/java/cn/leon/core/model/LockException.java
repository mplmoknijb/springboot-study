package cn.leon.core.model;

public class LockException extends RuntimeException {
    public LockException() {
        super();
    }

    public LockException(String message, Throwable t) {
        super(message, t);
    }

    public LockException(String message) {
        super(message);
    }

    public LockException(Throwable t) {
        super(t);
    }
}
