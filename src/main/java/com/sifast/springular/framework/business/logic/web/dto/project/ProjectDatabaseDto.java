package com.sifast.springular.framework.business.logic.web.dto.project;

import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;

public class ProjectDatabaseDto {

    private CreateProjectDto project;

    private CreateDatabaseDto database;

    public CreateProjectDto getProject() {
        return project;
    }

    public void setProject(CreateProjectDto project) {
        this.project = project;
    }

    public CreateDatabaseDto getDatabase() {
        return database;
    }

    public void setDatabase(CreateDatabaseDto database) {
        this.database = database;
    }

}
