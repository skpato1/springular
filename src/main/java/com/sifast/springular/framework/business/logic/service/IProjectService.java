package com.sifast.springular.framework.business.logic.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.common.ProjectStatus;
import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IProjectService extends IGenericService<Project, Integer> {

	List<Project> findByStatusProject(ProjectStatus projectStatus);
	
	
}
