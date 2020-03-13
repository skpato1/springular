package com.sifast.springular.framework.business.logic.common;


public class ApiStatus {

    public static final int STATUS_OK = 200;

    public static final int STATUS_CREATED = 201;

    public static final int STATUS_ACCEPTED = 202;

    public static final int STATUS_SERVER_ERROR = 500;

    public static final int STATUS_NOT_FOUND = 404;

    public static final int STATUS_BAD_REQUEST = 400;

    private ApiStatus() {
        super();
    }

}

