package com.sifast.springular.framework.business.logic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.SystemCommandExecutor;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;

@Service
public class CommandExecutorService implements ICommandExecutorService {

	@Value("${app.path}")
	private String appDirectory;

	@Value("${file.path}")
	private String fileJDL;

	@Value("${file.generate.path}")
	private String fileJdlToGenerate;

	@Override
	public String executeCommand(String cmd) throws IOException, InterruptedException {
		List<String> commands = new ArrayList<String>();
		commands.add(Constants.PATTERN_CMD_SH);
		commands.add(Constants.PATTERN_CMD_SH_OPTION_C);
		commands.add(cmd);
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		commandExecutor.executeCommand();
		StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
		StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
		
		if(stdout!=null)
		{
			return stdout.toString();

		}
		else
			return stderr.toString();
			


	}

	@Override
	public void createDataBase(String nameDB) throws IOException, InterruptedException {
		executeCommand(Constants.PATTERN_CMD_MYSQL_DB + nameDB + Constants.PATTERN_POINT_VIRGULE
				+ Constants.PATTERN_ANTI_SLASH);

	}

	@Override
	public void generateGithubProjectFromAngular(String projectName, String userName)
			throws IOException, InterruptedException {

		executeCommand(Constants.PATTERN_GIT_CLONE + projectName + Constants.PATTERN_SLASH + userName
				+ Constants.PATTERN_POINT_GIT);

	}

	@Override
	public void generateApplicationPropertiesFromAngular(String typeDB, String nameDB, String usernameDB, String pwdDB)
			throws IOException, InterruptedException {

		File file = new File(Constants.PATTERN_PATH_APPLICATION_PROPERTIES);
		String fileContext = FileUtils.readFileToString(file);
		fileContext = fileContext.replaceAll(Constants.PATTERN_DATABASE_NAME, nameDB);
		fileContext = fileContext.replaceAll(Constants.PATTERN_DATABASE_USERNAME, usernameDB);
		fileContext = fileContext.replaceAll(Constants.PATTERN_DATABASE_PASSWORD, pwdDB);
		FileUtils.write(file, fileContext);

	}

	@Override
	public void executeJdlFromTerminal(boolean isWindows) throws IOException, InterruptedException {
		String path = Constants.PATTERN_ENV_VAR;
		ProcessBuilder processBuilder = null;
		try {
			if (isWindows) {
				processBuilder = new ProcessBuilder("bash", "-c", "jhipster import-jdl " + fileJdlToGenerate);
			} else {
				processBuilder = new ProcessBuilder("sh", "-c", "jhipster import-jdl " + fileJdlToGenerate);
			}
			Map<String, String> env = processBuilder.environment();
			processBuilder.directory(new File(appDirectory));
			env.put("PATH", path);
			Process process = processBuilder.start();
			StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), System.out::println);
			Executors.newSingleThreadExecutor().submit(streamGobbler);
			process.waitFor();
			File file = new File(fileJdlToGenerate);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
