package com.sifast.springular.framework.business.logic.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.DtoFileWriter;
import com.sifast.springular.framework.business.logic.Executor.SystemCommandExecutor;
import com.sifast.springular.framework.business.logic.Executor.ZipUtility;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;

@Service
public class CommandExecutorService implements ICommandExecutorService {

	@Value("${app.path}")
	private String appDirectory;

	@Value("${file.path}")
	private String fileJDL;

	@Value("${file.generate.path}")
	private String fileJdlToGenerate;

	@Value("${variable.environment}")
	private String pathVaribleEnvironment;

	@Autowired
	DtoFileWriter dtoFileWriter;
	
	@Autowired
	ZipUtility zip;

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

		if (stdout != null) {
			return stdout.toString();

		} else
			return stderr.toString();

	}

	@Override
	public void createDataBase(String nameDB) throws IOException, InterruptedException {
		executeCommand(Constants.PATTERN_CMD_MYSQL_DB.concat(nameDB).concat(Constants.PATTERN_POINT_VIRGULE)
				.concat(Constants.PATTERN_ANTI_SLASH));

	}

	@Override
	public void cloneSpringularFrameworkSocleFromGitlab(Project project, boolean isWindows)
			throws IOException, InterruptedException {
		executeCommand(Constants.PATTERN_GIT_CLONE.concat(project.getUsernameProject()).concat(Constants.PATTERN_SLASH)
				.concat(Constants.OLD_PROJECT_NAME_TO_CLONE).concat(Constants.PATTERN_POINT_GIT));
		

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
		String path = pathVaribleEnvironment;
		ProcessBuilder processBuilder = null;
		try {
			if (isWindows) {
				processBuilder = new ProcessBuilder("bash", "-c", "jhipster import-jdl ".concat(fileJdlToGenerate)
						.concat(" ").concat(Constants.SKIP_INSTALL_NODE_MODULES));
			} else {
				processBuilder = new ProcessBuilder("sh", "-c", "jhipster import-jdl ".concat(fileJdlToGenerate)
						.concat(" ").concat(Constants.SKIP_INSTALL_NODE_MODULES));
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

	@Override
	public void copyEntitiesToGeneratedProject(Project project, boolean isWindows)
			throws IOException, InterruptedException {
		String entitiesFiles = extraireEntitiesFilesToCopy(project);
		executeCopyEntitiesCommandForDifferentOs(isWindows, entitiesFiles);
	}

	public void executeCopyEntitiesCommandForDifferentOs(boolean isWindows, String entitiesFiles)
			throws IOException, InterruptedException {
		if (isWindows) {
			executeCommand(Constants.COPY_COMMAND_WINDOWS.concat(Constants.PATH_TO_GENERATED_JHIPSTER_PROJECT)
					.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE));
		} else {

			executeCommand(Constants.COPY_COMMAND_LINUX_FILES
					.concat(Constants.PATH_TO_GENERATED_JHIPSTER_PROJECT_FOR_FILES).concat(entitiesFiles).concat("} ")
					.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE));
			System.out.println(Constants.COPY_COMMAND_LINUX_FILES
					.concat(Constants.PATH_TO_GENERATED_JHIPSTER_PROJECT_FOR_FILES).concat(entitiesFiles).concat("} ")
					.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE));	
		}
	}

	public String extraireEntitiesFilesToCopy(Project project) {
		List<BuisnessLogicEntity> entitiesToCopy = project.getEntities();
		String entitiesFiles = "{";
		for (int i = 0; i < entitiesToCopy.size(); i++) {
			entitiesFiles += entitiesToCopy.get(i).getNameEntity().concat(".java").concat(",");
		}
		entitiesFiles = charRemoveAt(entitiesFiles, entitiesFiles.length() - 1);
		return entitiesFiles;
	}

	public static String charRemoveAt(String str, int p) {
		return str.substring(0, p) + str.substring(p + 1);
	}

	@Override
	public void createFolderForEachDto(Project project) throws IOException, InterruptedException {
		project.getEntities().stream().forEach(entity -> {
			try {
				executeCommand(Constants.MKDIR_COMMAND
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
						.concat(entity.getNameEntity().toLowerCase()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	public void copyDaoToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException {
		String daoFiles = extraireDaoFilesToCopy(project);
		executeCopyDaoCommandForDifferentOs(isWindows, daoFiles);
	}

	private void executeCopyDaoCommandForDifferentOs(boolean isWindows, String daoFiles)
			throws IOException, InterruptedException {

		if (isWindows) {
			executeCommand(Constants.COPY_COMMAND_WINDOWS.concat(Constants.PATH_TO_GENERATED_DAO_JHIPSTER_PROJECT)
					.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DAO_PACKAGE));
		} else {

			executeCommand(Constants.COPY_COMMAND_LINUX_FILES
					.concat(Constants.PATH_TO_GENERATED_DAO_JHIPSTER_PROJECT_FILES).concat(daoFiles).concat("} ")
					.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DAO_PACKAGE));

		}

	}

	private String extraireDaoFilesToCopy(Project project) {
		List<BuisnessLogicEntity> daoToCopy = project.getEntities();
		String daoFiles = "{";
		for (int i = 0; i < daoToCopy.size(); i++) {
			daoFiles += daoToCopy.get(i).getNameEntity().concat("Repository.java").concat(",");
		}
		daoFiles = charRemoveAt(daoFiles, daoFiles.length() - 1);
		return daoFiles;
	}

	@Override
	public void renameDaoToGeneratedProject(Project project, boolean isWindows)
			throws IOException, InterruptedException {
		project.getEntities().stream().forEach(entity -> {
			try {
				executeCommand(Constants.RENAME_COMMAND_LINUX_FILES
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES)
						.concat(entity.getNameEntity().concat("Repository.java "))
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES).concat("I")
						.concat(entity.getNameEntity()).concat("Dao.java"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void copyEntitiesToDtoFolder(Project project, boolean isWindows) throws IOException, InterruptedException {
		String EntitiesToCopyFiles = extraireEntitiesFilesToCopy(project);
		System.out.println(EntitiesToCopyFiles);
		executeCopyEntitiesToDtoCommandForDifferentOs(isWindows, EntitiesToCopyFiles, project);
	}

	@Override
	public void renameDTo(Project project, boolean isWindows) throws IOException, InterruptedException {
		project.getEntities().stream().forEach(entity -> {
			try {
				executeCommand(Constants.RENAME_COMMAND_LINUX_FILES
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
						.concat(entity.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH)
						.concat(entity.getNameEntity()).concat(".java ")
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
						.concat(entity.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH)
						.concat(entity.getNameEntity()).concat("Dto.java"));
				dtoFileWriter.generateSuperFilesInEachFolderDTO(entity, project);

			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	private void executeCopyEntitiesToDtoCommandForDifferentOs(boolean isWindows, String entitiesToCopyFiles,
			Project project) throws IOException, InterruptedException {
		project.getEntities().stream().forEach(entity -> {

			try {
				if (isWindows) {
					executeCopyCommandWindows(entity);
				} else {

					executeCopyCommandLinux(entity);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		});

	}

	private void executeCopyCommandLinux(BuisnessLogicEntity entity) throws IOException, InterruptedException {
		executeCommand(Constants.COPY_COMMAND_LINUX_FILES_CP
				.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES).concat(entity.getNameEntity())
				.concat(".java ").concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
				.concat(entity.getNameEntity().toLowerCase()));
	}

	private void executeCopyCommandWindows(BuisnessLogicEntity entity) throws IOException, InterruptedException {
		executeCommand(
				Constants.COPY_COMMAND_WINDOWS.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES)
						.concat(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES)
						.concat(entity.getNameEntity().toLowerCase()));
	}

	@Override
	public void editNameProjectAfterCloning(Project project, boolean isWindows)
			throws IOException, InterruptedException {
			
						renameProjectFolder(project);
						editPomFilesWithTheNewProjectName(project);
			
	}

	private void editPomFilesWithTheNewProjectName(Project project) throws IOException {
		File pom = new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW
				.concat(project.getNameProject())
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.POM_XML));
		File  pomPersistence= new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW
				.concat(project.getNameProject())
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.SIFAST_SPRING_PERSISTENCE)
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.POM_XML));
		File pomService = new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW
				.concat(project.getNameProject())
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.SIFAST_SPRING_SERVICE)
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.POM_XML));
		File pomWeb = new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW
				.concat(project.getNameProject())
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.SIFAST_SPRING_WEB)
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.POM_XML));
		File pomCommon = new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW
				.concat(project.getNameProject())
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.SIFAST_SPRING_COMMON)
				.concat(Constants.PATTERN_SLASH)
				.concat(Constants.POM_XML));
		
		File applicationPropertiesFile = new File(Constants.APPLICATION_PROPERTIES_SPRINGULAR_DESKTOP);
		
		File swaggerConfigFile = new File(
				Constants.SWAGGER_CONFIG_FILE_PATH
				.concat(Constants.SWAGGER_CONFIG_FILE)
				);
		
		editPomFile(project, pom);
		editPomFile(project, pomPersistence);
		editPomFile(project, pomService);
		editPomFile(project, pomWeb);
		editPomFile(project, pomCommon);
		editPomFile(project, applicationPropertiesFile);
		editPomFile(project, swaggerConfigFile);



		
	}

	private void editPomFile(Project project, File file) throws IOException {
		String fileContext = FileUtils.readFileToString(file);
		fileContext = fileContext.replaceAll(Constants.OLD_PROJECT_NAME,project.getNameProject());
		fileContext = fileContext.replaceAll(Constants.OLD_PROJECT_NAME_ALT,project.getNameProject());
		FileUtils.write(file, fileContext);
	}

	private void renameProjectFolder(Project project) throws IOException, InterruptedException {
		executeCommand(
		 Constants.RENAME_COMMAND_LINUX_FILES
		.concat(Constants.PATH_TO_CLONED_PROJECT_SOURCE).concat(" ")
		.concat(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW)
		.concat(project.getNameProject())
		);
	}

	
	@Override
	public void zipProject(Project project) throws FileNotFoundException, IOException {
		List<File> folder = new ArrayList<File>();
		File file = new File(Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW+project.getNameProject());
		folder.add(file);
		zip.zip(folder, Constants.PATH_TO_CLONED_PROJECT_SOURCE_DESKTOP_NEW+project.getNameProject()+Constants.POINT_ZIP);
		
	}

	
	

}
