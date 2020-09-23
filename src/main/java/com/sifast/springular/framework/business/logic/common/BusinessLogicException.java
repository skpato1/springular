package com.sifast.springular.framework.business.logic.common;


public class BusinessLogicException extends Exception {

    private static final long serialVersionUID = 1L;

    public BusinessLogicException(String message) {
        super(message);
    }

    public BusinessLogicException(ApiMessage message) {
        super(message.toString());
    }

    public BusinessLogicException() {
    }
}
