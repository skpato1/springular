package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface IProjectApi {

	@ApiResponses(value = {
	@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.PROJECT_CREATED_SUCCESSFULLY, response = ViewProjectDto.class),
	@ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
	@ApiOperation(value = "save project", response = ViewProjectDto.class)
	@RequestMapping(value = "/project", method = RequestMethod.POST)
	ResponseEntity<Object> saveProject(CreateProjectDto projectDto, BindingResult bindingResult);

	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getProject(int id);

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	ResponseEntity<Object> getAllProjects();

	@RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteProject(int id);

	@RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateProject(int id, ProjectDto projectDto, BindingResult bindingResult);

}
