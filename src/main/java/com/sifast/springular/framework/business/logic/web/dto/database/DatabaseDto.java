package com.sifast.springular.framework.business.logic.web.dto.database;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DatabaseDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String nameDatabase;
    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String typeDatabase;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String usernameDatabase;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String passwordDatabase;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private int projectId;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public String getNameDatabase() {
        return nameDatabase;
    }

    public void setNameDatabase(String nameDatabase) {
        this.nameDatabase = nameDatabase;
    }

    public String getTypeDatabase() {
        return typeDatabase;
    }

    public void setTypeDatabase(String typeDatabase) {
        this.typeDatabase = typeDatabase;
    }


    public String getUsernameDatabase() {
        return usernameDatabase;
    }

    public void setUsernameDatabase(String usernameDatabase) {
        this.usernameDatabase = usernameDatabase;
    }

    public String getPasswordDatabase() {
        return passwordDatabase;
    }

    public void setPasswordDatabase(String passwordDatabase) {
        this.passwordDatabase = passwordDatabase;
    }

    @Override
    public String toString() {
        return "DatabaseDto [nameDatabase=" + nameDatabase + ", typeDatabase=" + typeDatabase + ", usernameDatabase="
                + usernameDatabase + ", passwordDatabase=" + passwordDatabase + ", project_id=" + projectId + "]";
    }


}
