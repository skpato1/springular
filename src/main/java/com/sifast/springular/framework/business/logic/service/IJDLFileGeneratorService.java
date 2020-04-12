package com.sifast.springular.framework.business.logic.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IJDLFileGeneratorService {

	public void generateProjectWithJdl(Project project) throws IOException;
	public void extendTimeStampInGeneratedEntities(Project project) throws IOException;
	public void deleteUnusedCommentsInGeneratedEntities(Project project)throws IOException;
	public void createFilesInEachFolderDTO(Project project)throws IOException;
	public void writeFilesIService(Project project) throws IOException;
	public void writeFilesService(Project project) throws IOException;
	public void writeFilesDao(Project project)throws IOException;
	public void writeFilesMappers(Project project)throws IOException;
	public void writeFilesInterfacesWebServicesApi(Project project)throws IOException;
	public void writeFilesWebServicesApiImpl(Project project)throws IOException;
	public void addConstantsInApiMessageFile(Project project)throws IOException;


}
