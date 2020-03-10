package com.sifast.convertToJava.tn.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.convertToJava.tn.entities.Project;
import com.sifast.convertToJava.tn.web.config.ConfiguredModelMapper;
import com.sifast.convertToJava.tn.web.dto.project.CreateProjectDto;
import com.sifast.convertToJava.tn.web.dto.project.ViewProjectDto;
@Component
public class ProjectMapper {
	
	@Autowired
    private ConfiguredModelMapper modelMapper;
	
	
	public Project mapCreateProject(CreateProjectDto projectDto) {
        Project mappedProject = modelMapper.map(projectDto, Project.class);
        return mappedProject;
    }
	
	 public ViewProjectDto mapProjectToViewProjectDto(Project project) {
	        ViewProjectDto viewProjectDto = modelMapper.map(project, ViewProjectDto.class);
	        return viewProjectDto;
	    }

}
