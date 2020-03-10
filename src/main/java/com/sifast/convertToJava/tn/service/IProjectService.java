package com.sifast.convertToJava.tn.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.convertToJava.tn.entities.Project;

@Transactional
public interface IProjectService extends IGenericService<Project, Integer> {

}
