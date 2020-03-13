package com.sifast.springular.framework.business.logic.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.SystemCommandExecutor;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;

@Service
public class CommandExecutorService implements ICommandExecutorService {
	@Override
	public void executeCommand(String cmd) throws IOException, InterruptedException {
		List<String> commands = new ArrayList<String>();
		commands.add(Constants.PATTERN_CMD_SH);
		commands.add(Constants.PATTERN_CMD_SH_OPTION_C);
		commands.add(cmd);
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		@SuppressWarnings("unused")
		int result = commandExecutor.executeCommand();

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

}