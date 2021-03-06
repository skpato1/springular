package com.sifast.springular.framework.business.logic.web.service.impl;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.ICommandExecutorService;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;
import com.sifast.springular.framework.business.logic.service.IProjectService;
import com.sifast.springular.framework.business.logic.web.service.api.IProjectGeneratorApi;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class ProjectGeneratorApi implements IProjectGeneratorApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectGeneratorApi.class);

    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

    Object httpResponseBody;

    HttpStatus httpStatus;

    @Autowired
    private IJDLFileGeneratorService jdlFileGeneratorService;

    @Autowired
    private ICommandExecutorService commandExecutorService;

    @Autowired
    private IProjectService projectService;

    @Override
    public byte[] zipAndDownloadProject(@PathVariable int id, HttpServletResponse response) throws IOException, InterruptedException {
        LOGGER.info("Web service generate Project invoked with project id {}", id);
        boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
        Optional<Project> project = projectService.findById(id);
        if (project.isPresent()) {

            commandExecutorService.cloneSpringularFrameworkSocleFromGitlab(project.get(), isWindows);

            commandExecutorService.editNameProjectAfterCloning(project.get(), isWindows);

            commandExecutorService.createFolderForEachDto(project.get());

            jdlFileGeneratorService.generateProjectWithJdl(project.get());

            commandExecutorService.executeJdlFromTerminal(isWindows);

            commandExecutorService.copyEntitiesToGeneratedProject(project.get(), isWindows);

            jdlFileGeneratorService.extendTimeStampInGeneratedEntities(project.get());

            jdlFileGeneratorService.deleteUnusedCommentsInGeneratedEntities(project.get());

            commandExecutorService.copyDaoToGeneratedProject(project.get(), isWindows);

            commandExecutorService.renameDaoToGeneratedProject(project.get(), isWindows);

            jdlFileGeneratorService.addConstantsInApiMessageFile(project.get());

            jdlFileGeneratorService.writeFilesDao(project.get());

            jdlFileGeneratorService.writeFilesIService(project.get());

            jdlFileGeneratorService.writeFilesService(project.get());

            jdlFileGeneratorService.writeValidators(project.get());

            commandExecutorService.copyEntitiesToDtoFolder(project.get(), isWindows);

            commandExecutorService.renameDTo(project.get(), isWindows);

            jdlFileGeneratorService.createFilesInEachFolderDTO(project.get());

            jdlFileGeneratorService.writeFilesMappers(project.get());

            jdlFileGeneratorService.writeEntityAuditService(project.get());

            jdlFileGeneratorService.writeFilesInterfacesWebServicesApi(project.get());

            jdlFileGeneratorService.writeFilesWebServicesApiImpl(project.get());

            commandExecutorService.deleteGeneratedJHipsterProjectContent(isWindows);

            commandExecutorService.deleteClonedProjectSocleFromGeneratedProject(project.get(), isWindows);

            commandExecutorService.deleteClonedProjectSocleFromGenerator(isWindows);

            response.setStatus(HttpServletResponse.SC_OK);

            response.addHeader("Content-Disposition", "attachment; filename=\"" + project.get().getNameProject() + ".zip\"");

            return commandExecutorService.zipProject(project.get());
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new byte[]{};
    }

}
