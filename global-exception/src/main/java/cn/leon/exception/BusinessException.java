package cn.leon.exception;

import java.io.Serializable;

public class BusinessException extends RuntimeException implements Serializable {


    private static final long serialVersionUID = 5940322516487961765L;

    /**
     * 生成序列异常时
     */
    public static final BusinessException DB_GET_SEQ_NEXT_VALUE_ERROR = new BusinessException(10040007, "序列生成超时");

    /**
     * 具体异常码
     */
    protected int code;

    /**
     * 异常信息
     */
    protected String msg;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException newInstance(String msgFormat, Object... args) {
        return new BusinessException(this.code, msgFormat, args);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
