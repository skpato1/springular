package com.sifast.springular.framework.business.logic.web.service.api;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface IProjectApi {

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.PROJECT_CREATED_SUCCESSFULLY, response = ViewProjectDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "save project", response = ViewProjectDto.class)
    @PostMapping(value = "/project")
    ResponseEntity<?> saveProject(CreateProjectDto projectDto, BindingResult bindingResult);

    @GetMapping(value = "/project/{id}")
    ResponseEntity<?> getProject(int id);

    @GetMapping(value = "/projects")
    ResponseEntity<?> getAllProjects();

    @GetMapping(value = "/projects/validated")
    ResponseEntity<?> getAllValidatedProjects();

    @GetMapping(value = "/project/validate/{id}")
    ResponseEntity<?> validateProject(int id);

    @DeleteMapping(value = "/project/{id}")
    ResponseEntity<?> deleteProject(int id);

    @PutMapping(value = "/project/{id}")
    ResponseEntity<?> updateProject(int id, ProjectDto projectDto, BindingResult bindingResult);

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.PROJECT_CREATED_SUCCESSFULLY, response = ProjectDatabaseDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "save project and database", response = ViewProjectDto.class)
    @PostMapping(value = "/project/database")
    ResponseEntity<?> saveProjectAndDatabase(ProjectDatabaseDto projectDatabaseDto, BindingResult bindingResult);

}
