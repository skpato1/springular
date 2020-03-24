package com.sifast.springular.framework.business.logic.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IJDLFileGeneratorService {

	public void generateProjectWithJdl(Project project) throws IOException;
	public void extendTimeStampInGeneratedEntities(Project project) throws IOException;
	public void deleteUnusedCommentsInGeneratedEntities(Project project)throws IOException;
	public void importTimeStampEntityInGeneratedEntities(Project project)throws IOException;


}
