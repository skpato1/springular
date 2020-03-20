package com.sifast.springular.framework.business.logic.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IJhipsterService {

	public void generateProjectWithJdl(Project project) throws IOException;
}
