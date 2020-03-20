package com.sifast.springular.framework.business.logic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.common.ProjectStatus;
import com.sifast.springular.framework.business.logic.dao.ProjectDao;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IProjectService;

@Service
public class ProjectService extends GenericService<Project, Integer> implements IProjectService {

	@Autowired
	private ProjectDao projectDao;
	
	
	@Override
	public List<Project> findByStatusProject(ProjectStatus projectStatus) {
		
		return projectDao.findByStatusProject(projectStatus);
	}
	
	
	

}
