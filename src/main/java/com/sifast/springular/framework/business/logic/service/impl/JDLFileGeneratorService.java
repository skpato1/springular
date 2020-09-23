package com.sifast.springular.framework.business.logic.service.impl;

import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.executor.AuditServiceFileWriter;
import com.sifast.springular.framework.business.logic.executor.DaoFileWriter;
import com.sifast.springular.framework.business.logic.executor.DtoFileWriter;
import com.sifast.springular.framework.business.logic.executor.FileWrite;
import com.sifast.springular.framework.business.logic.executor.IServiceFileWriter;
import com.sifast.springular.framework.business.logic.executor.IWebServiceApi;
import com.sifast.springular.framework.business.logic.executor.MapperFileWriter;
import com.sifast.springular.framework.business.logic.executor.ValidatorWriter;
import com.sifast.springular.framework.business.logic.executor.WebServiceApiImpl;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JDLFileGeneratorService implements IJDLFileGeneratorService {

    @Value("${file.generate.path}")
    private String fileJdlToGenerate;

    @Autowired
    FileWrite fileWriter;

    @Autowired
    DtoFileWriter dtoFileWriter;

    @Autowired
    IServiceFileWriter serviceFileWriter;

    @Autowired
    DaoFileWriter daoFileWriter;

    @Autowired
    MapperFileWriter mapperFileWriter;

    @Autowired
    IWebServiceApi iWebServiceApi;

    @Autowired
    WebServiceApiImpl webServiceApiImpl;

    @Autowired
    ValidatorWriter validatorWriter;

    @Autowired
    AuditServiceFileWriter auditServiceFileWriter;

    @Override
    public void generateProjectWithJdl(Project project) throws IOException {
        FileWriter myWriter = new FileWriter(fileJdlToGenerate);
        fileWriter.writeProjectDetailsInJdlFile(project, myWriter);
        fileWriter.writeEntitiesInJdlFile(project, myWriter);
        fileWriter.writeRelationshipsInJdlFile(project, myWriter);
        myWriter.close();
    }

    @Override
    public void extendTimeStampInGeneratedEntities(Project project) {
        project.getEntities().stream().forEach(entity -> {
            try {
                File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES)
                        + entity.getNameEntity().concat(".java"));
                String fileContext = FileUtils.readFileToString(file);
                fileContext = fileContext.replace("springular.framework.domain", "model");
                fileContext = fileContext.replace(Constants.IMPLEMENTS, Constants.EXTEND_TIMESTAMP_ENTITY);
                FileUtils.write(file, fileContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void deleteUnusedCommentsInGeneratedEntities(Project project) {
        project.getEntities().stream().forEach(entity -> {
            try {
                File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES)
                        .concat(entity.getNameEntity().concat(".java")));
                String fileContext = FileUtils.readFileToString(file);
                fileContext = fileContext.replace(Constants.UNUSED_COMMENTS_FOR_ATTRIBUE, "");
                fileContext = fileContext.replace(Constants.UNUSED_COMMENTS_FOR_METHOD, "");
                FileUtils.write(file, fileContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void createFilesInEachFolderDTO(Project project) {
        dtoFileWriter.generateViewFilesInEachFolderDTO(project);
    }

    @Override
    public void writeFilesIService(Project project) {
        serviceFileWriter.writeIServiceFiles(project);
    }

    @Override
    public void writeFilesService(Project project) {
        serviceFileWriter.writeImplementServiceFiles(project);
    }

    @Override
    public void writeFilesDao(Project project) {
        daoFileWriter.writeDaoFiles(project);
    }

    @Override
    public void writeFilesMappers(Project project) {
        project.getEntities().stream().forEach(entity ->
                mapperFileWriter.generateMapperFiles(entity, project)
        );

    }

    @Override
    public void writeFilesInterfacesWebServicesApi(Project project) {
        project.getEntities().stream().forEach(entity ->
                iWebServiceApi.generateIWebServiceApiFiles(entity, project)
        );

    }

    @Override
    public void writeFilesWebServicesApiImpl(Project project) {
        project.getEntities().stream().forEach(entity ->
                webServiceApiImpl.generateWebServicesImplFiles(entity, project)
        );
    }

    @Override
    public void addConstantsInApiMessageFile(Project project) {
        project.getEntities().stream().forEach(entity -> {
            try {
                File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_COMMON).concat("ApiMessage.java"));
                String fileContext = FileUtils.readFileToString(file);
                if (!fileContext.contains(entity.getNameEntity().toUpperCase().concat(Constants.NOT_FOUND))) {
                    fileContext = fileContext.replaceFirst("\\{",
                            "\\{".concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                                    .concat(Constants.CONSTANTS_DECLARATION).concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND).concat(Constants.EGALE)
                                    .concat(Constants.DOUBLE_COTE).concat(entity.getNameEntity().toUpperCase()).concat(Constants.NOT_FOUND).concat(Constants.DOUBLE_COTE)
                                    .concat(Constants.PATTERN_POINT_VIRGULE));
                    if (!fileContext.contains(entity.getNameEntity().toUpperCase().concat(Constants.CREATED_SUCCESSFULLY))) {
                        fileContext = fileContext.replaceFirst("\\{",
                                "\\{".concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                                        .concat(Constants.CONSTANTS_DECLARATION).concat(entity.getNameEntity().toUpperCase()).concat(Constants.CREATED_SUCCESSFULLY)
                                        .concat(Constants.EGALE).concat(Constants.DOUBLE_COTE).concat(entity.getNameEntity().toUpperCase()).concat(Constants.CREATED_SUCCESSFULLY)
                                        .concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_POINT_VIRGULE));
                    }
                }

                FileUtils.write(file, fileContext);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void writeValidators(Project project) {
        validatorWriter.generateValidators(project);
    }

    @Override
    public void writeEntityAuditService(Project project) {
        project.getEntities().forEach(entity -> {
            if (Boolean.TRUE.equals(entity.getIsTrackable())) {
                auditServiceFileWriter.generateAuditServiceFiles(entity, project);
            }
        });

    }

}
