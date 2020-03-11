package com.sifast.springular.framework.business.logic.common;import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;

public class ApiMessage {

    public static final String SUCCESSFUL_OPERATION = "SUCCESSFUL_OPERATION";

    public static final String NO_DATA = "NO_DATA";

    public static final String INVALID_INPUT = "INVALID_INPUT";

    public static final String CREATED_SUCCESSFULLY = "CREATED_SUCCESSFULLY";

    public static final String NOT_FOUND = "NOT_FOUND";

    public static final String VALIDATED_SUCCESSFULLY = "VALIDATED_SUCCESSFULLY";

    public static final String REQUIRED_VALIDATION_FAILED = "REQUIRED_VALIDATION_FAILED";

    public static final String SERVER_ERROR_OCCURRED = "SERVER_ERROR_OCCURRED";

    public static final String ERR_SAVE = "ERR_SAVE";

    public static final String UPDATED_SUCCESSFULLY = "UPDATED_SUCCESSFULLY";

    public static final String ERR_UPDATE = "ERR_UPDATE";

    public static final String DELETED_SUCCESSFULLY = "DELETED_SUCCESSFULLY";

    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";

    public static final String EMPLOYEE_NOT_CONNECTED = "EMPLOYEE_NOT_CONNECTED";

    public static final String CRON_IDENTIFIER = "CRON_IDENTIFIER";

    public static final String USER_ALREADY_EXIT = "USER_ALREADY_EXIT";

    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

    public static final String OAUTH2SCHEMA = "oauth2schema";

    public static final String NO_TRACE = "NO_TRACE";

    public static final String USER_IS_SUPER_ADMIN = "USER_IS_SUPER_ADMIN";

    public static final String ROLE_IS_SUPER_ADMIN = "ROLE_IS_SUPER_ADMIN";

    public static final String CONNECTED_USER = "CONNECTED_USER";

    public static final String SYSTEM = "SYSTEM";

    public static final String EMPTY_MESSAGE = "";

    public static final String STRING_SIZE_VALIDATION_FAILED = "STRING_SIZE_VALIDATION_FAILED";

    public static final String WRONG_OLD_PASSWORD = "WRONG_OLD_PASSWORD";

    public static final String PASSWORDS_ARE_NOT_MATCHING = "PASSWORDS_ARE_NOT_MATCHING";

    public static final String PROJECT_NOT_FOUND = "PROJECT_NOT_FOUND";

	public static final String ATTRIBUTE_NOT_FOUND = "ATTRIBUTE_NOT_FOUND";
			
			
    private ApiMessage() {
        super();
    }

}

