package com.sifast.springular.framework.business.logic.web.mapper;

import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public Project mapCreateProject(CreateProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }

    public ViewProjectDto mapProjectToViewProjectDto(Project project) {
        return modelMapper.map(project, ViewProjectDto.class);
    }

    public Project mapDatabaseDtoToModelDatabase(ProjectDto projectDto) {
        return modelMapper.map(projectDto, Project.class);
    }

}
