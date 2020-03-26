package com.sifast.springular.framework.business.logic.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface ICommandExecutorService {
	
	
	public String executeCommand(String cmd) throws IOException, InterruptedException;
	public void createDataBase(String nameDB) throws IOException, InterruptedException;
	public void cloneSpringularFrameworkSocleFromGitlab(Project project,boolean isWindows) throws IOException, InterruptedException;
	public void generateApplicationPropertiesFromAngular(String typeDB, String nameDB, String usernameDB, String pwdDB) throws IOException, InterruptedException;
	public void executeJdlFromTerminal(boolean isWindows) throws IOException, InterruptedException;
	public void copyEntitiesToGeneratedProject(Project project,boolean isWindows) throws IOException, InterruptedException;
	public void createFolderForEachDto(Project project)throws IOException, InterruptedException;
	public void copyDaoToGeneratedProject(Project project,boolean isWindows) throws IOException, InterruptedException;
	public void renameDaoToGeneratedProject(Project project,boolean isWindows) throws IOException, InterruptedException;

}
