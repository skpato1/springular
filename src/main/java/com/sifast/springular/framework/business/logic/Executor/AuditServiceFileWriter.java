package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.service.impl.RelationshipService;

@Component
public class AuditServiceFileWriter {

    @Autowired
    RelationshipService relationshipService;

    public void generateAuditServiceFiles(BuisnessLogicEntity entity, Project project) {

        project.getEntities().stream().forEach(ent -> {
            String viewFileDto = "View".concat(ent.getNameEntity()).concat("Dto");
            String nameFileAuditService = ent.getNameEntity().concat("AuditService");
            String serviceFile = "I".concat(ent.getNameEntity()).concat("Service");
            String serviceVariable = ent.getNameEntity().toLowerCase().concat("Service");
            String auditServiceFile = "Audit".concat("Service");
            String auditServiceVariable = "audit".concat("Service");
            String entityMapper = ent.getNameEntity().concat("Mapper");
            String variableEntityMapper = ent.getNameEntity().toLowerCase().concat("Mapper");

            try {
                List<Relationship> relationsMaster = relationshipService.findByMasterEntity(ent);
                List<Relationship> relationsSlave = relationshipService.findBySlaveEntity(ent);
                List<BuisnessLogicEntity> entitiesMaster = relationsSlave.stream().map(Relationship::getMasterEntity).collect(Collectors.toList());
                List<BuisnessLogicEntity> entitiesSlave = relationsMaster.stream().map(Relationship::getSlaveEntity).collect(Collectors.toList());
                FileWriter myWriter = writeImportsAndStructureOfClassInAuditServiceFiles(project, ent, nameFileAuditService, serviceFile, viewFileDto);
                injectServicesAndMappers(ent, myWriter, serviceFile, serviceVariable, auditServiceFile, auditServiceVariable, entityMapper, variableEntityMapper);
                List<String> variables = declareUsefulVariables(ent, myWriter);
                writeMethodCompareTwoVersionsOfTheSameObject(ent, myWriter, variables);
                writeMethodGetSortedVersions(ent, myWriter, viewFileDto);
                writeMethodGetVersionFromAllVersions(myWriter, viewFileDto);
                writeMethodGetEntityFromAllVersions(myWriter, ent, viewFileDto);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeMethodGetEntityFromAllVersions(FileWriter myWriter, BuisnessLogicEntity ent, String viewFileDto) {
        // TODO Auto-generated method stub

    }

    private void writeMethodGetVersionFromAllVersions(FileWriter myWriter, String viewFileDto) {
        // TODO Auto-generated method stub

    }

    private void writeMethodGetSortedVersions(BuisnessLogicEntity ent, FileWriter myWriter, String viewFileDto) {
        // TODO Auto-generated method stub

    }

    private void writeMethodCompareTwoVersionsOfTheSameObject(BuisnessLogicEntity ent, FileWriter myWriter, List<String> variables) {
        // TODO Auto-generated method stub

    }

    private List<String> declareUsefulVariables(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String counter = "i";
        String variable1 = ent.getNameEntity().toLowerCase().concat("1");
        String variable2 = ent.getNameEntity().toLowerCase().concat("2");
        String version1 = "version1";
        String version2 = "version2";
        retourAlaLigneAndTabulation(myWriter);
        List<String> result = new ArrayList<String>();
        declareIntegerCounter(myWriter, counter);
        retourAlaLigneAndTabulation(myWriter);
        declareVariableEntityAndInitializeNull(ent, myWriter, variable1);
        retourAlaLigneAndTabulation(myWriter);
        declareVariableEntityAndInitializeNull(ent, myWriter, variable2);
        retourAlaLigneAndTabulation(myWriter);
        declareVersionDtoAndInitializeNull(myWriter, version1);
        retourAlaLigneAndTabulation(myWriter);
        declareVersionDtoAndInitializeNull(myWriter, version2);
        result.add(variable1);
        result.add(variable2);
        result.add(version1);
        result.add(version2);
        return result;
    }

    private void declareVersionDtoAndInitializeNull(FileWriter myWriter, String version1) throws IOException {
        myWriter.write(Constants.VERSION_DTO.concat(Constants.INFERIEUR).concat(Constants.POINT_INTERROGATION).concat(Constants.SUPERIEUR).concat(" ").concat(version1)
                .concat(Constants.EGALE).concat(Constants.NULL).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void declareVariableEntityAndInitializeNull(BuisnessLogicEntity ent, FileWriter myWriter, String variable1) throws IOException {
        myWriter.write(ent.getNameEntity().concat(" ").concat(variable1).concat(Constants.EGALE).concat(Constants.NULL).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void declareIntegerCounter(FileWriter myWriter, String counter) throws IOException {
        myWriter.write(Constants.PRIVATE.concat(Constants.INT).concat(" ").concat(counter).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void injectServicesAndMappers(BuisnessLogicEntity ent, FileWriter myWriter, String serviceFile, String serviceVariable, String auditServiceFile,
            String auditServiceVariable, String entityMapper, String variableEntityMapper) throws IOException {
        autoWiredEntityService(myWriter, serviceFile, serviceVariable);
        autoWiredAuditService(myWriter, auditServiceFile, auditServiceVariable);
        autoWiredEntityMapper(myWriter, entityMapper, variableEntityMapper);

    }

    private void autoWiredEntityMapper(FileWriter myWriter, String entityMapper, String variableEntityMapper) throws IOException {
        autoWiredAnnotation(myWriter);
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(entityMapper.concat(" ").concat(variableEntityMapper));
    }

    private void autoWiredEntityService(FileWriter myWriter, String serviceFile, String serviceVariable) throws IOException {
        autoWiredAnnotation(myWriter);
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(serviceFile.concat(" ").concat(serviceVariable));
    }

    private void autoWiredAuditService(FileWriter myWriter, String auditServiceFile, String auditServiceVariable) throws IOException {
        autoWiredAnnotation(myWriter);
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(auditServiceFile.concat(" ").concat(auditServiceFile));
    }

    private void retourAlaLigneAndTabulation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
    }

    private void autoWiredAnnotation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
    }

    private FileWriter writeImportsAndStructureOfClassInAuditServiceFiles(Project project, BuisnessLogicEntity ent, String nameFileAuditService, String viewFileDto,
            String serviceFile) throws IOException {

        File file = new File(
                ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_AUDIT_SERVICE).concat(nameFileAuditService).concat(".java"));
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(ConstantsImportPackage.PACKAGE_SERVICE_AUDIT.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_AUTOWIRED);
        myWriter.write(ConstantsImportPackage.IMPORT_COLLECTORS);
        myWriter.write(ConstantsImportPackage.IMPORT_HASH_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_SET);
        myWriter.write(ConstantsImportPackage.IMPORT_ARRAY_LIST);
        myWriter.write(ConstantsImportPackage.IMPORT_LIST);
        myWriter.write(ConstantsImportPackage.IMPORT_OPTIONAL);
        myWriter.write(ConstantsImportPackage.IMPORT_COMPARATOR);
        myWriter.write(ConstantsImportPackage.IMPORT_HTTP_STATUS);
        myWriter.write(ConstantsImportPackage.IMPORT_RESPONSE_ENTITY);
        myWriter.write(ConstantsImportPackage.IMPORT_ANNOTATION_SERVICE);
        myWriter.write(ConstantsImportPackage.IMPORT_MAPPER.concat(ent.getNameEntity()).concat("Mapper").concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(viewFileDto).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(ent.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_INTERFACE_SERVICE.concat(serviceFile).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_VERSION_DTO);
        myWriter.write(ConstantsImportPackage.IMPORT_VERSION_DIFF_DTO);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_SERVICE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(nameFileAuditService).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));

        return myWriter;

    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();
    }
}
