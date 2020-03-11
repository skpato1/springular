package com.sifast.springular.framework.business.logic.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
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
