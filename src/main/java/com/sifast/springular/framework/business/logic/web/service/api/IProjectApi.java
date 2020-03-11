package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;


public interface IProjectApi {

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	ResponseEntity<Object> saveProject(CreateProjectDto projectDto, BindingResult bindingResult);

	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getProject(int id);

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	ResponseEntity<Object> getAllProjects();
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.DELETE)
	ResponseEntity<Object> deleteProject(int id);
	
	@RequestMapping(value = "/project/{id}", method = RequestMethod.PUT)
	ResponseEntity<Object> updateProject(int id,ProjectDto projectDto, BindingResult bindingResult);

}
