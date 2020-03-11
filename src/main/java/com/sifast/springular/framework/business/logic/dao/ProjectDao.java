package com.sifast.springular.framework.business.logic.dao;

import org.springframework.stereotype.Repository;

import com.sifast.springular.framework.business.logic.entities.Project;


@Repository
public interface ProjectDao extends IGenericDao<Project, Integer> {

}
