package com.sifast.springular.framework.business.logic.web.service.impl;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.common.ProjectStatus;
import com.sifast.springular.framework.business.logic.entities.Database;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IDatabaseService;
import com.sifast.springular.framework.business.logic.service.IProjectService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import com.sifast.springular.framework.business.logic.web.mapper.DatabaseMapper;
import com.sifast.springular.framework.business.logic.web.mapper.ProjectMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IProjectApi;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class ProjectApi implements IProjectApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectApi.class);

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

    @Autowired
    private ConfiguredModelMapper modelMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DatabaseMapper databaseMapper;

    @Autowired
    private IProjectService projectService;

    @Autowired
    private IDatabaseService databaseService;

    @Override
    public ResponseEntity<?> saveProject(@ApiParam(required = true, value = "projectDto", name = "projectDto") @RequestBody CreateProjectDto projectDto,
            BindingResult bindingResult) {
        LOGGER.info("Web service saveProject invoked with projectDto {}", projectDto);
        try {
            Project savedProject = projectService.save(projectMapper.mapCreateProject(projectDto));
            httpStatus = HttpStatus.OK;
            httpResponseBody = modelMapper.map(savedProject, ViewProjectDto.class);
        } catch (Exception e) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            httpResponseBody = httpErrorResponse;
        }

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> saveProjectAndDatabase(
            @ApiParam(required = true, value = "ProjectDatabaseDto", name = "ProjectDatabaseDto") @RequestBody ProjectDatabaseDto projectDatabaseDto, BindingResult bindingResult) {
        LOGGER.info("Web service saveProject invoked with projectDto {}", projectDatabaseDto);
        try {
            Project savedProject = null;
            if (projectDatabaseDto.getProject() != null) {
                savedProject = projectService.save(projectMapper.mapCreateProject(projectDatabaseDto.getProject()));
                if (projectDatabaseDto.getDatabase() != null) {
                    Database databaseToBeSaved = databaseMapper.mapCreateDatabase(projectDatabaseDto.getDatabase());
                    savedProject.setDatabase(databaseToBeSaved);
                    databaseToBeSaved.setProject(savedProject);
                    databaseService.save(databaseToBeSaved);
                }
            }
            httpStatus = HttpStatus.OK;
            httpResponseBody = modelMapper.map(savedProject, ViewProjectDto.class);
        } catch (Exception e) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            httpResponseBody = httpErrorResponse;
        }

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getProject(
            @ApiParam(value = "ID of Project that needs to be fetched ", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service getProject invoked with id {}", id);

        Optional<Project> project = projectService.findById(id);

        if (project.isPresent()) {
            httpStatus = HttpStatus.OK;
            httpResponseBody = projectMapper.mapProjectToViewProjectDto(project.get());
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getAllProjects() {
        List<Project> projects = projectService.findAll();
        httpStatus = HttpStatus.OK;
        httpResponseBody = !projects.isEmpty() ? projects.stream().map(project -> modelMapper.map(project, ViewProjectDto.class)).collect(Collectors.toList())
                : Collections.emptyList();
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> deleteProject(
            @ApiParam(value = "ID of Project that needs to be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service deleteProject invoked with id {}", id);
        Optional<Project> preDeleteProject = projectService.findById(id);
        if (!preDeleteProject.isPresent()) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {

            projectService.delete(preDeleteProject.get());
            httpStatus = HttpStatus.OK;
            LOGGER.info("INFO level message: Project with id = {} deleted ", id);

        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> updateProject(
            @ApiParam(value = "ID of Project that needs to be updated", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id,
            @ApiParam(required = true, value = "projectDto", name = "projectDto") @RequestBody ProjectDto projectDto, BindingResult bindingResult) {
        LOGGER.info("Web service updateProject invoked with id {}", id);
        if (!bindingResult.hasFieldErrors()) {
            Optional<Project> project = projectService.findById(id);
            if (project.isPresent()) {
                Project mappedProject = projectMapper.mapDatabaseDtoToModelDatabase(projectDto);
                mappedProject.setId(id);
                Project updatedProject = projectService.save(mappedProject);
                httpResponseBody = projectMapper.mapProjectToViewProjectDto(updatedProject);
                httpStatus = HttpStatus.OK;
                LOGGER.info("INFO level message: Project updated {}", updatedProject);

            } else {

                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            }
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getAllValidatedProjects() {
        List<Project> projects = projectService.findByStatusProject(ProjectStatus.VALIDATED);
        httpStatus = HttpStatus.OK;
        httpResponseBody = !projects.isEmpty() ? projects.stream().map(project -> modelMapper.map(project, ViewProjectDto.class)).collect(Collectors.toList())
                : Collections.emptyList();
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> validateProject(@PathVariable int id) {
        Optional<Project> project = projectService.findById(id);

        if (project.isPresent()) {
            project.get().setStatusProject(ProjectStatus.VALIDATED);
            Project projectToBeReturned = projectService.save(project.get());
            httpStatus = HttpStatus.OK;
            httpResponseBody = projectMapper.mapProjectToViewProjectDto(projectToBeReturned);
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);

    }


}
