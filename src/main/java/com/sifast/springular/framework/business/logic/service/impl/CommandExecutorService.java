package com.sifast.springular.framework.business.logic.service.impl;

import com.sifast.springular.framework.business.logic.common.constants.CommandConstantsLinux;
import com.sifast.springular.framework.business.logic.common.constants.CommandConstantsWindows;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsModules;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.executor.DtoFileWriter;
import com.sifast.springular.framework.business.logic.executor.SystemCommandExecutor;
import com.sifast.springular.framework.business.logic.executor.ZipUtility;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CommandExecutorService implements ICommandExecutorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandExecutorService.class);

    @Value("${app.path}")
    private String appDirectory;

    @Value("${path.generated-project}")
    private String pathToGeneratedProject;

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
        LOGGER.debug("executeCommand : {}", cmd);
        List<String> commands = new ArrayList<>();
        commands.add(CommandConstantsLinux.PATTERN_CMD_SH);
        commands.add(Constants.PATTERN_CMD_SH_OPTION_C);
        commands.add(cmd);
        SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
        commandExecutor.executeCommand();
        StringBuilder stdout = commandExecutor.getStandardOutputFromCommand();
        StringBuilder stderr = commandExecutor.getStandardErrorFromCommand();
        if (stdout != null) {
            return stdout.toString();
        } else {
            return stderr.toString();
        }
    }

    @Override
    public void createDataBase(String nameDB) throws IOException, InterruptedException {
        LOGGER.debug("createDataBase");
        executeCommand(Constants.PATTERN_CMD_MYSQL_DB.concat(nameDB).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_ANTI_SLASH));

    }

    @Override
    public void cloneSpringularFrameworkSocleFromGitlab(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("cloneSpringularFrameworkSocleFromGitlab");
        executeCommand(Constants.PATTERN_GIT_CLONE.concat(project.getUsernameProject()).concat(Constants.PATTERN_SLASH).concat(Constants.OLD_PROJECT_NAME_TO_CLONE)
                .concat(Constants.PATTERN_POINT_GIT));

    }

    @Override
    public void generateApplicationPropertiesFromAngular(Project project, String typeDB, String nameDB, String usernameDB, String pwdDB) throws IOException, InterruptedException {
        LOGGER.debug("generateApplicationPropertiesFromAngular");
        File file = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(ConstantsPath.PATTERN_PROJECT_PATH_APPLICATION_PROPERTIES));
        String fileContext = FileUtils.readFileToString(file);
        fileContext = fileContext.replace(Constants.PATTERN_DATABASE_NAME, nameDB);
        fileContext = fileContext.replace(Constants.PATTERN_DATABASE_USERNAME, usernameDB);
        fileContext = fileContext.replace(Constants.PATTERN_DATABASE_PASSWORD, pwdDB);
        FileUtils.write(file, fileContext);

    }

    @Override
    public void executeJdlFromTerminal(boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("executeJdlFromTerminal");
        String path = pathVaribleEnvironment;
        ProcessBuilder processBuilder = null;
        try {
            if (isWindows) {
                processBuilder = new ProcessBuilder("bash", "-c", "jhipster import-jdl ".concat(fileJdlToGenerate).concat(" ").concat(Constants.SKIP_INSTALL_NODE_MODULES));
            } else {
                processBuilder = new ProcessBuilder("sh", "-c", "jhipster import-jdl ".concat(fileJdlToGenerate).concat(" ").concat(Constants.SKIP_INSTALL_NODE_MODULES));
            }
            Map<String, String> env = processBuilder.environment();
            processBuilder.directory(new File(appDirectory));
            env.put("PATH", path);
            Process process = processBuilder.start();
            StreamGobbler streamGobbler = new StreamGobbler(process.getInputStream(), LOGGER::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void copyEntitiesToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("copyEntitiesToGeneratedProject");
        String entitiesFiles = extraireEntitiesFilesToCopy(project);
        executeCopyEntitiesCommandForDifferentOs(project, isWindows, entitiesFiles);
    }

    public void executeCopyEntitiesCommandForDifferentOs(Project project, boolean isWindows, String entitiesFiles) throws IOException, InterruptedException {
        LOGGER.debug("executeCopyEntitiesCommandForDifferentOs");
        if (isWindows) {
            executeCommand(CommandConstantsWindows.COPY_COMMAND_WINDOWS.concat(appDirectory + ConstantsPath.PATH_TO_GENERATED_JHIPSTER_PROJECT).concat(pathToGeneratedProject)
                    .concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE));
        } else {
            String[] splitted = entitiesFiles.substring(1).split(",");
            for (String entity : splitted) {
                executeCommand(
                        CommandConstantsLinux.COPY_COMMAND_LINUX_FILES.concat(appDirectory + ConstantsPath.PATH_TO_GENERATED_JHIPSTER_PROJECT_FOR_FILES).concat(entity).concat(" ")
                                .concat(pathToGeneratedProject).concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE));
            }
        }
    }

    public String extraireEntitiesFilesToCopy(Project project) {
        LOGGER.debug("extraireEntitiesFilesToCopy");
        List<BuisnessLogicEntity> entitiesToCopy = project.getEntities();
        StringBuilder entitiesFiles = new StringBuilder("{");
        for (int i = 0; i < entitiesToCopy.size(); i++) {
            entitiesFiles.append(entitiesToCopy.get(i).getNameEntity().concat(".java").concat(","));
        }
        return charRemoveAt(entitiesFiles.toString(), entitiesFiles.length() - 1);
    }

    public static String charRemoveAt(String str, int p) {
        return str.substring(0, p) + str.substring(p + 1);
    }

    @Override
    public void createFolderForEachDto(Project project) throws IOException, InterruptedException {
        LOGGER.debug("createFolderForEachDto");
        project.getEntities().stream().forEach(entity -> {
            try {
                executeCommand(Constants.MKDIR_COMMAND.concat(pathToGeneratedProject).concat(project.getNameProject())
                        .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES).concat(entity.getNameEntity().toLowerCase()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void copyDaoToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("copyDaoToGeneratedProject");
        String daoFiles = extraireDaoFilesToCopy(project);
        executeCopyDaoCommandForDifferentOs(project, isWindows, daoFiles);
    }

    private void executeCopyDaoCommandForDifferentOs(Project project, boolean isWindows, String daoFiles) throws IOException, InterruptedException {
        LOGGER.debug("executeCopyDaoCommandForDifferentOs");
        if (isWindows) {
            executeCommand(CommandConstantsWindows.COPY_COMMAND_WINDOWS.concat(appDirectory + ConstantsPath.PATH_TO_GENERATED_DAO_JHIPSTER_PROJECT).concat(pathToGeneratedProject)
                    .concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DAO_PACKAGE));
        } else {

            executeCommand(
                    CommandConstantsLinux.COPY_COMMAND_LINUX_FILES.concat(appDirectory + ConstantsPath.PATH_TO_GENERATED_DAO_JHIPSTER_PROJECT_FILES).concat(daoFiles).concat("} ")
                            .concat(pathToGeneratedProject).concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DAO_PACKAGE));

        }

    }

    private String extraireDaoFilesToCopy(Project project) {
        LOGGER.debug("extraireDaoFilesToCopy");
        List<BuisnessLogicEntity> daoToCopy = project.getEntities();
        StringBuilder daoFiles = new StringBuilder("{");
        for (int i = 0; i < daoToCopy.size(); i++) {
            daoFiles.append(daoToCopy.get(i).getNameEntity().concat("Repository.java").concat(","));
        }
        return charRemoveAt(daoFiles.toString(), daoFiles.length() - 1);
    }

    @Override
    public void renameDaoToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("renameDaoToGeneratedProject");
        project.getEntities().stream().forEach(entity -> {
            try {
                executeCommand(CommandConstantsLinux.RENAME_COMMAND_LINUX_FILES.concat(pathToGeneratedProject).concat(project.getNameProject())
                        .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES).concat(entity.getNameEntity().concat("Repository.java "))
                        .concat(pathToGeneratedProject).concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES).concat("I")
                        .concat(entity.getNameEntity()).concat("Dao.java"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void copyEntitiesToDtoFolder(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("copyEntitiesToDtoFolder");
        executeCopyEntitiesToDtoCommandForDifferentOs(isWindows, project);
    }

    @Override
    public void renameDTo(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("renameDTo");
        project.getEntities().stream().forEach(entity -> {
            try {
                executeCommand(CommandConstantsLinux.RENAME_COMMAND_LINUX_FILES.concat(pathToGeneratedProject).concat(project.getNameProject())
                        .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES).concat(entity.getNameEntity().toLowerCase())
                        .concat(Constants.PATTERN_SLASH).concat(entity.getNameEntity()).concat(".java ").concat(pathToGeneratedProject).concat(project.getNameProject())
                        .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES).concat(entity.getNameEntity().toLowerCase())
                        .concat(Constants.PATTERN_SLASH).concat(entity.getNameEntity()).concat("Dto.java"));


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        dtoFileWriter.generateSuperFilesInEachFolderDTO(project);
    }

    private void executeCopyEntitiesToDtoCommandForDifferentOs(boolean isWindows, Project project) {
        LOGGER.debug("executeCopyEntitiesToDtoCommandForDifferentOs");
        project.getEntities().stream().forEach(entity -> {

            try {
                if (isWindows) {
                    executeCopyCommandWindows(project, entity);
                } else {

                    executeCopyCommandLinux(project, entity);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    private void executeCopyCommandLinux(Project project, BuisnessLogicEntity entity) throws IOException, InterruptedException {
        LOGGER.debug("executeCopyCommandLinux");
        executeCommand(CommandConstantsLinux.COPY_COMMAND_LINUX_FILES_CP.concat(pathToGeneratedProject).concat(project.getNameProject())
                .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES).concat(entity.getNameEntity()).concat(".java ").concat(pathToGeneratedProject)
                .concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES).concat(entity.getNameEntity().toLowerCase()));
    }

    private void executeCopyCommandWindows(Project project, BuisnessLogicEntity entity) throws IOException, InterruptedException {
        LOGGER.debug("executeCopyCommandWindows");
        executeCommand(CommandConstantsWindows.COPY_COMMAND_WINDOWS.concat(pathToGeneratedProject).concat(project.getNameProject())
                .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES).concat(pathToGeneratedProject).concat(project.getNameProject())
                .concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES).concat(entity.getNameEntity().toLowerCase()));
    }

    @Override
    public void editNameProjectAfterCloning(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("editNameProjectAfterCloning");
        renameProjectFolder(project);
        editPomFilesWithTheNewProjectName(project);

    }

    private void editPomFilesWithTheNewProjectName(Project project) throws IOException {
        LOGGER.debug("editPomFilesWithTheNewProjectName");
        File pom = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(Constants.PATTERN_SLASH).concat(Constants.POM_XML));
        File pomPersistence = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(Constants.PATTERN_SLASH)
                .concat(ConstantsModules.SIFAST_SPRING_PERSISTENCE).concat(Constants.PATTERN_SLASH).concat(Constants.POM_XML));
        File pomService = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(Constants.PATTERN_SLASH)
                .concat(ConstantsModules.SIFAST_SPRING_SERVICE).concat(Constants.PATTERN_SLASH).concat(Constants.POM_XML));
        File pomWeb = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(Constants.PATTERN_SLASH)
                .concat(ConstantsModules.SIFAST_SPRING_WEB).concat(Constants.PATTERN_SLASH).concat(Constants.POM_XML));
        File pomCommon = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(Constants.PATTERN_SLASH)
                .concat(ConstantsModules.SIFAST_SPRING_COMMON).concat(Constants.PATTERN_SLASH).concat(Constants.POM_XML));

        File applicationPropertiesFile = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(ConstantsPath.APPLICATION_PROPERTIES_PROJECT_DESKTOP));

        File swaggerConfigFile = new File(
                pathToGeneratedProject.concat(project.getNameProject()).concat(ConstantsPath.SWAGGER_PROJECT_CONFIG_FILE_PATH).concat(Constants.SWAGGER_CONFIG_FILE));

        editPomFile(project, pom);
        editPomFile(project, pomPersistence);
        editPomFile(project, pomService);
        editPomFile(project, pomWeb);
        editPomFile(project, pomCommon);
        editPomFile(project, applicationPropertiesFile);
        editPomFile(project, swaggerConfigFile);

    }

    private void editPomFile(Project project, File file) throws IOException {
        LOGGER.debug("editPomFile");
        String fileContext = FileUtils.readFileToString(file);
        fileContext = fileContext.replace(Constants.OLD_PROJECT_NAME, project.getNameProject());
        fileContext = fileContext.replace(Constants.OLD_PROJECT_NAME_ALT, project.getNameProject());
        FileUtils.write(file, fileContext);
    }

    private void renameProjectFolder(Project project) throws IOException, InterruptedException {
        LOGGER.debug("renameProjectFolder");
        executeCommand(CommandConstantsLinux.RENAME_COMMAND_LINUX_FILES.concat(ConstantsPath.PATH_TO_CLONED_PROJECT_SOURCE).concat(" ")
                .concat(pathToGeneratedProject).concat(project.getNameProject()));
    }

    @Override
    public byte[] zipProject(Project project) throws IOException {
        LOGGER.debug("zipProject");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
        ZipOutputStream zipOutputStream = new ZipOutputStream(bufferedOutputStream);
        ArrayList<File> files = new ArrayList<>(2);
        File file = new File(pathToGeneratedProject + project.getNameProject());
        files.add(file);
        for (File fileToZip : files) {
            zip.zipFile(fileToZip, fileToZip.getName(), zipOutputStream);
        }
        zipOutputStream.finish();
        zipOutputStream.flush();
        IOUtils.closeQuietly(zipOutputStream);
        IOUtils.closeQuietly(bufferedOutputStream);
        IOUtils.closeQuietly(byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public void deleteGeneratedJHipsterProjectContent(boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("deleteGeneratedJHipsterProjectContent");
        if (isWindows) {
            executeCommand(CommandConstantsLinux.REMOVE_COMMAND_WINDOWS.concat("/S /Q ").concat(appDirectory.replace("/", "\\")).concat(Constants.ETOILE));
        } else {
            executeCommand(CommandConstantsLinux.REMOVE_COMMAND_LINUX.concat("-rf ").concat(appDirectory).concat("&& ").concat(CommandConstantsLinux.CREATE_FOLDER_COMMAND_LINUX)
                    .concat(appDirectory));
        }
    }

    @Override
    public void deleteClonedProjectSocleFromGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("deleteClonedProjectSocleFromGeneratedProject");
        if (isWindows) {
            executeCommand(
                    CommandConstantsLinux.REMOVE_COMMAND_WINDOWS.concat("/Q ").concat(pathToGeneratedProject).concat(project.getNameProject())
                            .concat(Constants.PATTERN_SLASH).concat(Constants.OLD_PROJECT_NAME_TO_CLONE));
        } else {
            executeCommand(
                    CommandConstantsLinux.REMOVE_COMMAND_LINUX.concat("-rf ").concat(pathToGeneratedProject).concat(project.getNameProject())
                            .concat(Constants.PATTERN_SLASH).concat(Constants.OLD_PROJECT_NAME_TO_CLONE));
        }
    }

    @Override
    public void deleteClonedProjectSocleFromGenerator(boolean isWindows) throws IOException, InterruptedException {
        LOGGER.debug("deleteClonedProjectSocleFromGenerator");
        if (isWindows) {
            executeCommand(CommandConstantsLinux.REMOVE_COMMAND_WINDOWS.concat("/Q ").concat(ConstantsPath.PATH_TO_CLONED_PROJECT_SOURCE));
        } else {
            executeCommand(
                    CommandConstantsLinux.REMOVE_COMMAND_LINUX.concat("-rf ").concat(ConstantsPath.PATH_TO_CLONED_PROJECT_SOURCE));
        }
    }

}
