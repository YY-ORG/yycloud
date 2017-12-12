package com.yy.cloud.common.utils;

import lombok.Data;

/**
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     12/11/17 7:50 PM<br/>
 *
 * @author chenxj
 * @see
 * @since JDK 1.8
 */
@Data
public class YYException extends Exception {
    private String code;
    public YYException(String _code) {
        super();
        this.code = _code;
    }

    public YYException(String _code, String message) {
        super(message);
        this.code = _code;
    }

    public YYException(String _code, String message, Throwable cause) {
        super(message, cause);
        this.code = _code;
    }

    public YYException(String _code, Throwable cause) {
        super(cause);
        this.code = _code;
    }

    protected YYException(String _code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = _code;
    }
}
