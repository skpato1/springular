package com.sifast.convertToJava.tn.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.convertToJava.tn.web.dto.project.CreateProjectDto;


public interface IProjectApi {

	@RequestMapping(value = "/project", method = RequestMethod.POST)
	ResponseEntity<Object> saveProject(CreateProjectDto projectDto, BindingResult bindingResult);

	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	ResponseEntity<Object> getProject(int id);

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	ResponseEntity<Object> getAllProjects();

}
