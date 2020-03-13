package com.sifast.springular.framework.business.logic.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IProjectService extends IGenericService<Project, Integer> {

}
