package com.sifast.springular.framework.business.logic.common;


public enum HttpCostumCode {
    NOT_FOUND(44), BAD_REQUEST(4), SERVER_ERROR(5), TOKEN_EXPIRED(43), UNAUTHORIZED(3), IS_USED(49);

    private int code;

    HttpCostumCode(int message) {
        this.code = message;
    }

    public int getValue() {
        return code;
    }
}
