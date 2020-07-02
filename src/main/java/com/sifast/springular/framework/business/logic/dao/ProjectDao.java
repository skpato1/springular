package com.sifast.springular.framework.business.logic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sifast.springular.framework.business.logic.common.ProjectStatus;
import com.sifast.springular.framework.business.logic.entities.Project;


@Repository
public interface ProjectDao extends IGenericDao<Project, Integer> {
	
	List<Project> findByStatusProject(ProjectStatus projectStatus);

}
