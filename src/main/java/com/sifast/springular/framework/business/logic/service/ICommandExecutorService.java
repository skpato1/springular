package com.sifast.springular.framework.business.logic.service;

import com.sifast.springular.framework.business.logic.entities.Project;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ICommandExecutorService {

    public String executeCommand(String cmd) throws IOException, InterruptedException;

    public void createDataBase(String nameDB) throws IOException, InterruptedException;

    public void cloneSpringularFrameworkSocleFromGitlab(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void generateApplicationPropertiesFromAngular(Project project, String typeDB, String nameDB, String usernameDB, String pwdDB) throws IOException, InterruptedException;

    public void executeJdlFromTerminal(boolean isWindows) throws IOException, InterruptedException;

    public void copyEntitiesToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void createFolderForEachDto(Project project) throws IOException, InterruptedException;

    public void copyDaoToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void renameDaoToGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void copyEntitiesToDtoFolder(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void renameDTo(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void editNameProjectAfterCloning(Project project, boolean isWindows) throws IOException, InterruptedException;

    public byte[] zipProject(Project project) throws IOException;

    public void deleteGeneratedJHipsterProjectContent(boolean isWindows) throws InterruptedException, IOException;

    public void deleteClonedProjectSocleFromGeneratedProject(Project project, boolean isWindows) throws IOException, InterruptedException;

    public void deleteClonedProjectSocleFromGenerator(boolean isWindows) throws IOException, InterruptedException;
}
