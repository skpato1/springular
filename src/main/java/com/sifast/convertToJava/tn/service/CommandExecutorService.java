package com.sifast.convertToJava.tn.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.sifast.convertToJava.tn.Executor.SystemCommandExecutor;

@Service
public class CommandExecutorService {
	
	String db_springular="db_springular";
	String root="root";
	String sifast2014="password=sifast2014";
	
	public void executeCommand(String cmd) throws IOException, InterruptedException {
		List<String> commands = new ArrayList<String>();
		commands.add("/bin/sh");
		commands.add("-c");
		commands.add(cmd);
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		@SuppressWarnings("unused")
		int result = commandExecutor.executeCommand();
		StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
		StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
		System.out.println("STDOUT");
		System.out.println(stdout);

		System.out.println("STDERR");
		System.out.println(stderr);
	}

	public void createDataBase(String nameDB) throws IOException, InterruptedException {
		System.out.println("2/");
		executeCommand("/Applications/xampp/xamppfiles/bin/mysql -uroot --execute \"create DATABASE " + nameDB + ";\"");


	}

	public void generateGithubProjectFromAngular(String projectName, String userName)
			throws IOException, InterruptedException {
		System.out.println("1/");

		executeCommand("git clone http://git.sifast.com/" + projectName + "/" + userName + ".git");

	}

	public void generateApplicationPropertiesFromAngular(String typeDB, String nameDB, String usernameDB, String pwdDB)
			throws IOException, InterruptedException {
		System.out.println("3/");
		
		File file = new File("/Users/qc/Desktop/springular-framework/springular-framework-web/src/main/resources/application.properties");
		String fileContext = FileUtils.readFileToString(file);
		fileContext = fileContext.replaceAll(db_springular, nameDB);
		fileContext = fileContext.replaceAll(root, usernameDB);
		fileContext = fileContext.replaceAll("password=sifast2014", "password=" + pwdDB);
		FileUtils.write(file, fileContext);

	}



}
