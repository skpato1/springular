package com.sifast.convertToJava.tn.service;

import java.io.IOException;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICommandExecutorService {
	
	
	public void executeCommand(String cmd) throws IOException, InterruptedException;
	public void createDataBase(String nameDB) throws IOException, InterruptedException;
	public void generateGithubProjectFromAngular(String projectName, String userName) throws IOException, InterruptedException;
	public void generateApplicationPropertiesFromAngular(String typeDB, String nameDB, String usernameDB, String pwdDB) throws IOException, InterruptedException;

}
