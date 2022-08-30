package cn.refacter.easy.http.exception;

/**
 * @author refacter
 * Date：Create in 2022/8/30 21:43
 */
public class EasyHttpRuntimeException extends RuntimeException {
    public EasyHttpRuntimeException(String message) {
        super(message);
    }

    public EasyHttpRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyHttpRuntimeException(Throwable cause) {
        super(cause);
    }
}
