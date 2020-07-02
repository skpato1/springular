package com.sifast.springular.framework.business.logic.common;



public class BuisnessLogicException extends Exception {

	private static final long serialVersionUID = 1L;

	public BuisnessLogicException(String message) {
        super(message);
    }

    public BuisnessLogicException(ApiMessage message) {
        super(message.toString());
    }

    public BuisnessLogicException() {
    }
}
