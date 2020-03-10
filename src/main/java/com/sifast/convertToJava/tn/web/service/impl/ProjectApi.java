package com.sifast.convertToJava.tn.web.service.impl;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.convertToJava.tn.entities.Project;
import com.sifast.convertToJava.tn.service.IProjectService;
import com.sifast.convertToJava.tn.web.config.ConfiguredModelMapper;
import com.sifast.convertToJava.tn.web.dto.project.CreateProjectDto;
import com.sifast.convertToJava.tn.web.dto.project.ViewProjectDto;
import com.sifast.convertToJava.tn.web.mapper.ProjectMapper;
import com.sifast.convertToJava.tn.web.service.api.IProjectApi;


@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class ProjectApi implements IProjectApi{
	
	
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectApi.class);

    private Object httpResponseBody;

    private HttpStatus httpStatus;
    
	 	@Autowired
	    private ConfiguredModelMapper modelMapper;

	    @Autowired
	    private ProjectMapper projectMapper;
	    
	    @Autowired
	    private IProjectService projectService;

	
	
	@Override
	public ResponseEntity<Object> saveProject(@RequestBody CreateProjectDto projectDto, BindingResult bindingResult) {
        LOGGER.info("Web service saveProject invoked with projectDto {}", projectDto);
        try {
        Project savedProject = projectService.save(projectMapper.mapCreateProject(projectDto));
        httpStatus = HttpStatus.OK;
        httpResponseBody = modelMapper.map(savedProject, ViewProjectDto.class);
        }
        catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        

        return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> getProject(int id) {
		LOGGER.info("Web service getProject invoked with id {}", id);

        Optional<Project> project = projectService.findById(id);
        if (project.isPresent()) {
            httpStatus = HttpStatus.OK;
            httpResponseBody = projectMapper.mapProjectToViewProjectDto(project.get());
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	@Override
	public ResponseEntity<Object> getAllProjects() {
			List<Project> Projects = projectService.findAll();
	        httpStatus = HttpStatus.OK;
	        httpResponseBody = !Projects.isEmpty() ? Projects.stream().map(Project -> modelMapper.map(Project, ViewProjectDto.class)).collect(Collectors.toList()) : Collections.emptyList();
	        return new ResponseEntity<>(httpResponseBody, httpStatus);
	}

	
	
}
