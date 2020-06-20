package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Attribute;
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
            String viewFileDtoVariable = ent.getNameEntity().toLowerCase().concat("Dto");
            String nameFileAuditService = ent.getNameEntity().concat("AuditService");
            String serviceFile = "I".concat(ent.getNameEntity()).concat("Service");
            String serviceVariable = ent.getNameEntity().toLowerCase().concat("Service");
            String auditServiceFile = "Audit".concat("Service");
            String auditServiceVariable = "audit".concat("Service");
            String entityMapper = ent.getNameEntity().concat("Mapper");
            String variableEntityMapper = ent.getNameEntity().toLowerCase().concat("Mapper");
            try {
                FileWriter myWriter = writeImportsAndStructureOfClassInAuditServiceFiles(project, ent, nameFileAuditService, serviceFile, viewFileDto);
                injectServicesAndMappers(ent, myWriter, serviceFile, serviceVariable, auditServiceFile, auditServiceVariable, entityMapper, variableEntityMapper);
                List<String> variables = declareUsefulVariables(ent, myWriter);
                writeMethodCompareTwoVersionsOfTheSameObject(ent, myWriter, variables, viewFileDto);
                writeMethodGetSortedVersions(ent, myWriter, viewFileDto, viewFileDtoVariable, serviceVariable, auditServiceVariable, entityMapper, variableEntityMapper);
                writeMethodGetVersionFromAllVersions(myWriter, viewFileDto);
                writeMethodGetEntityFromAllVersions(myWriter, ent, viewFileDto, project);
                closeAccoladeAndFile(myWriter);
                writeViewImportsAfterAddRelationshipsAttribute(ent, project);
                injectChildMapper(ent, project, entityMapper, variableEntityMapper);
                importInjectedChildMapper(ent, project, entityMapper, variableEntityMapper);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void writeMethodGetEntityFromAllVersions(FileWriter myWriter, BuisnessLogicEntity ent, String viewFileDto, Project project) throws IOException {
        retourAlaLigneAndTabulation(myWriter);
        String nameMethod = Constants.GET_LOWERCASE.concat(ent.getNameEntity()).concat("FromAllVersions");
        String typeDeRetourDeMethod = ent.getNameEntity();
        String entryTypeVariable = Constants.VERSION_DTO.concat(Constants.INFERIEUR).concat(viewFileDto).concat(Constants.SUPERIEUR);
        String entryVariable = ent.getNameEntity().toLowerCase().concat("Version");
        String variableToInstance = ent.getNameEntity().toLowerCase();
        writeSignatureOfMethodWithOneEntryVariable(myWriter, nameMethod, typeDeRetourDeMethod, entryTypeVariable, entryVariable);
        tabulation(myWriter);
        instanciateClassWithEmptyConstructor(myWriter, typeDeRetourDeMethod, variableToInstance);
        tabulation(myWriter);
        tabulation(myWriter);
        setAttributesOfEntityFromEntryVariable(myWriter, ent, entryVariable, variableToInstance);
        if (isThisMasterEntityAndOneToManyRelationship(ent.getNameEntity(), ent.getRelationshipsMaster())) {
            tabulation(myWriter);
            tabulation(myWriter);
            callMethodCreateAndSetChildToTheEntityParent(myWriter, entryVariable, variableToInstance);
        }
        returnResult(myWriter, variableToInstance);
        closeConditionAccolade(myWriter);
        if (isThisMasterEntityAndOneToManyRelationship(ent.getNameEntity(), ent.getRelationshipsMaster())) {
            retourAlaLigneAndTabulation(myWriter);
            writeSignatureOfMethodWithTwoEntryVariable(myWriter, Constants.CREATE_AND_SET_CHILD_TO_THE_ENTITY_PARENT_METHOD, Constants.VOID, entryTypeVariable, entryVariable,
                    ent.getNameEntity(), variableToInstance);
            if (existEntityInMasterTableOneToMany(ent.getNameEntity(), ent.getRelationshipsMaster()) != null) {
                String nameSlaveEntity = existEntityInMasterTableOneToMany(ent.getNameEntity(), ent.getRelationshipsMaster());
                String firstLetterView = nameSlaveEntity.charAt(0) + "";
                String attributeToLowerCase = firstLetterView.toLowerCase() + nameSlaveEntity.substring(1, nameSlaveEntity.length()).toLowerCase();
                String attributeSelaveEntityName = attributeToLowerCase + "s";
                String firstLetter = attributeSelaveEntityName.charAt(0) + "";
                String getterAttribute = firstLetter.toUpperCase().concat(attributeSelaveEntityName.substring(1));
                tabulation(myWriter);
                instanciateSetOfClassWithEmptyConstructor(myWriter, nameSlaveEntity, attributeSelaveEntityName);
                tabulation(myWriter);
                String element_forEach = "element";
                myWriter.write(entryVariable.concat(Constants.POINT).concat(Constants.GET_ENTITY_METHOD).concat(Constants.POINT).concat(Constants.GET_LOWERCASE)
                        .concat(getterAttribute).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT).concat(Constants.FOR_EACH)
                        .concat(Constants.PARENTHESE_OUVRANTE).concat(element_forEach).concat(Constants.FLECHE).concat(Constants.ACCOLADE_OUVRANT)
                        .concat(Constants.PATTERN_RETOUR_LIGNE));
                tabulation(myWriter);
                tabulation(myWriter);
                myWriter.write(Constants.TRY.concat(Constants.ACCOLADE_OUVRANT));
                tabulation(myWriter);
                tabulation(myWriter);
                retourAlaLigneAndTabulation(myWriter);
                String childPostCommentMapper = nameSlaveEntity.toLowerCase().concat("Mapper");
                String methodToMapViewChildEntityDtoToChildEntity = "mapView".concat(nameSlaveEntity).concat("DtoTo").concat(nameSlaveEntity);
                myWriter.write(attributeSelaveEntityName.concat(Constants.POINT).concat(Constants.ADD).concat(Constants.PARENTHESE_OUVRANTE).concat(childPostCommentMapper)
                        .concat(Constants.POINT).concat(methodToMapViewChildEntityDtoToChildEntity).concat(Constants.PARENTHESE_OUVRANTE).concat(element_forEach)
                        .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                tabulation(myWriter);
                closeConditionAccolade(myWriter);
                tabulation(myWriter);
                tabulation(myWriter);
                myWriter.write(Constants.CATCH_EXCEPTION);
                tabulation(myWriter);
                tabulation(myWriter);
                myWriter.write(Constants.CLOSE_FOREACH);
                retourAlaLigneAndTabulation(myWriter);
                myWriter.write(variableToInstance.concat(Constants.POINT).concat(Constants.SET_LOWERCASE.concat(getterAttribute)).concat(Constants.PARENTHESE_OUVRANTE)
                        .concat(attributeSelaveEntityName).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
                closeConditionAccolade(myWriter);
            }
            // writeViewImportsAfterAddRelationshipsAttribute(ent, project);

        }
    }

    private void callMethodCreateAndSetChildToTheEntityParent(FileWriter myWriter, String entryVariable, String variableToInstance) throws IOException {
        myWriter.write(Constants.CREATE_AND_SET_CHILD_TO_THE_ENTITY_PARENT_METHOD.concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.VIRGULE)
                .concat(variableToInstance).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void setAttributesOfEntityFromEntryVariable(FileWriter myWriter, BuisnessLogicEntity ent, String entryVariable, String variableToInstance) {
        ent.getAttributes().forEach(attribute -> {
            String att = firstLetterUpperCase(attribute);
            String getAttribute = Constants.GET_LOWERCASE.concat(att).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE);
            String setAttribute = Constants.SET_LOWERCASE.concat(att);
            try {
                setEntity(myWriter, entryVariable, variableToInstance, getAttribute, setAttribute);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void instanciateClassWithEmptyConstructor(FileWriter myWriter, String typeDeRetourDeMethod, String variableToInstance) throws IOException {
        myWriter.write(typeDeRetourDeMethod.concat(" ").concat(variableToInstance).concat(Constants.EGALE).concat(Constants.NEW).concat(typeDeRetourDeMethod)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void instanciateSetOfClassWithEmptyConstructor(FileWriter myWriter, String typeDeRetourDeMethod, String variableToInstance) throws IOException {
        myWriter.write(AttributesTypeEnum.Set.toString().concat(Constants.INFERIEUR).concat(typeDeRetourDeMethod).concat(Constants.SUPERIEUR).concat(" ").concat(variableToInstance)
                .concat(Constants.EGALE).concat(Constants.HASHSET_DECLARATION).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void writeSignatureOfMethodWithOneEntryVariable(FileWriter myWriter, String nameMethod, String typeDeRetourDeMethod, String entryTypeVariable, String entryVariable)
            throws IOException {
        myWriter.write(Constants.PUBLIC.concat(typeDeRetourDeMethod).concat(" ").concat(nameMethod).concat(Constants.PARENTHESE_OUVRANTE).concat(entryTypeVariable).concat(" ")
                .concat(entryVariable).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void writeSignatureOfMethodWithTwoEntryVariable(FileWriter myWriter, String nameMethod, String typeDeRetourDeMethod, String entryTypeVariable, String entryVariable,
            String entryTypeVariableSecond, String entryVariableSecond) throws IOException {
        myWriter.write(Constants.PUBLIC.concat(typeDeRetourDeMethod).concat(" ").concat(nameMethod).concat(Constants.PARENTHESE_OUVRANTE).concat(entryTypeVariable).concat(" ")
                .concat(entryVariable).concat(Constants.VIRGULE).concat(entryTypeVariableSecond).concat(" ").concat(entryVariableSecond).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void writeMethodGetVersionFromAllVersions(FileWriter myWriter, String viewFileDto) throws IOException {
        retourAlaLigneAndTabulation(myWriter);
        String entryVariable = "action";
        String versionToReturn = "version";
        String getAuthor = Constants.GET_AUTHOR;
        String setAuthor = Constants.SET_AUTHOR;
        String getCreatedAt = Constants.GET_CREATEDAT;
        String setCreatedAt = Constants.SET_CREATEDAT;
        String getUpdatedAt = Constants.GET_UPDATEDAT;
        String setUpdatedAt = Constants.SET_UPDATEDAT;
        String getVersion = Constants.GET_VERSION;
        String setVersion = Constants.SET_VERSION;
        writeSignatureOfMethodGetVersionFromAllVersions(myWriter, viewFileDto, entryVariable);
        tabulation(myWriter);
        instanceOfVersionDTO(myWriter, versionToReturn);
        tabulation(myWriter);
        tabulation(myWriter);
        setVerion(myWriter, entryVariable, versionToReturn, getAuthor, setAuthor);
        tabulation(myWriter);
        tabulation(myWriter);
        setVerion(myWriter, entryVariable, versionToReturn, getCreatedAt, setCreatedAt);
        tabulation(myWriter);
        tabulation(myWriter);
        setVerion(myWriter, entryVariable, versionToReturn, getUpdatedAt, setUpdatedAt);
        tabulation(myWriter);
        tabulation(myWriter);
        setVerion(myWriter, entryVariable, versionToReturn, getVersion, setVersion);
        returnResult(myWriter, entryVariable);
        closeConditionAccolade(myWriter);

    }

    private void setVerion(FileWriter myWriter, String entryVariable, String versionToReturn, String getAuthor, String setAuthor) throws IOException {
        myWriter.write(versionToReturn.concat(Constants.POINT).concat(setAuthor).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(getAuthor).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void setEntity(FileWriter myWriter, String entryVariable, String versionToReturn, String getAuthor, String setAuthor) throws IOException {
        myWriter.write(versionToReturn.concat(Constants.POINT).concat(setAuthor).concat(Constants.PARENTHESE_OUVRANTE).concat(entryVariable).concat(Constants.POINT)
                .concat(Constants.GET_ENTITY_METHOD).concat(Constants.POINT).concat(getAuthor).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void instanceOfVersionDTO(FileWriter myWriter, String versionToReturn) throws IOException {
        myWriter.write(Constants.VERSION_DTO.concat(Constants.INFERIEUR).concat(Constants.POINT_INTERROGATION).concat(Constants.SUPERIEUR).concat(" ").concat(versionToReturn)
                .concat(Constants.EGALE).concat(Constants.NEW).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(Constants.SUPERIEUR)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private String writeSignatureOfMethodGetVersionFromAllVersions(FileWriter myWriter, String viewFileDto, String entryVariable) throws IOException {
        myWriter.write(Constants.PUBLIC.concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(Constants.POINT_INTERROGATION).concat(Constants.SUPERIEUR).concat(" ")
                .concat(Constants.METHOD_GET_VERSION_FROM_ALL_VERSIONS).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR)
                .concat(viewFileDto).concat(Constants.SUPERIEUR).concat(" ").concat(entryVariable).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));
        return entryVariable;
    }

    private void writeMethodGetSortedVersions(BuisnessLogicEntity ent, FileWriter myWriter, String viewFileDto, String viewFileDtoVariable, String serviceVariable,
            String auditServiceVariable, String entityMapper, String variableEntityMapper) throws IOException {
        writeSignatureGetSortedVersionsMethod(myWriter, viewFileDto);
        tabulation(myWriter);
        initializeCounter(myWriter);
        tabulation(myWriter);
        String viewDtoVariable = declareVariableToretrieveListOfAllVersionsOfEntity(myWriter, viewFileDto, viewFileDtoVariable);
        tabulation(myWriter);
        String retrievedEntity = retrieveOptionalEntityFromMethodFindById(myWriter, ent, serviceVariable);
        tabulation(myWriter);
        conditionIfretrievedOptionalEntityIsPresent(myWriter, retrievedEntity, auditServiceVariable, viewFileDto, entityMapper, viewDtoVariable, variableEntityMapper);
        tabulation(myWriter);
        returnStreamFilter(myWriter, viewDtoVariable);
        tabulation(myWriter);
        elseCondition(myWriter);
        returnResult(myWriter, Constants.NULL);
        closeConditionAccolade(myWriter);
    }

    private void elseCondition(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.ELSE.concat(" ").concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        tabulation(myWriter);
        tabulation(myWriter);
        myWriter.write(
                Constants.NEW.concat(" ").concat(Constants.RESPONSE_ENTITY_RESULT).concat(Constants.INFERIEUR).concat(Constants.SUPERIEUR).concat(Constants.PARENTHESE_OUVRANTE)
                        .concat(Constants.HTTP_STATUS_NOT_FOUND_RESPONSE_ENTITY).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        tabulation(myWriter);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
    }

    private void returnStreamFilter(FileWriter myWriter, String viewDtoVariable) throws IOException {
        String element_filter = "element";
        myWriter.write(viewDtoVariable.concat(Constants.POINT).concat(Constants.STREAM_METHOD).concat(Constants.POINT).concat(Constants.FILTER)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(element_filter).concat(Constants.FLECHE).concat(element_filter).concat(Constants.POINT)
                .concat(Constants.GET_ENTITY_METHOD).concat(Constants.POINT).concat(Constants.GET_ID_METHOD).concat(Constants.EGALE_CONDITION).concat(Constants.ID_MINUS)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT).concat(Constants.COLLECT_METHOD).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.COLLECTORS_TO_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.VERSION_DTO).concat(Constants.QUATRE_POINTS)
                .concat(Constants.GET_CREATEDAT_FILTER).concat(Constants.VIRGULE).concat(Constants.FILTER_BY_DATE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT)
                .concat(Constants.VALUES_METHOD).concat(Constants.POINT).concat(Constants.STREAM_METHOD).concat(Constants.POINT).concat(Constants.COLLECT_METHOD)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.COLLECTORS_TO_MAP).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.VERSION_DTO)
                .concat(Constants.QUATRE_POINTS).concat(Constants.GET_AUTHOR_FILTER).concat(Constants.VIRGULE).concat(Constants.FILTER_BY_DATE)
                .concat(Constants.PARENTHESE_FERMANTE).concat("").concat("").concat(Constants.POINT).concat(Constants.VALUES_METHOD).concat(Constants.POINT)
                .concat(Constants.STREAM_METHOD).concat(Constants.POINT).concat(Constants.SORTED).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.COMPARATOR_COMPARING)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.VERSION_DTO).concat(Constants.QUATRE_POINTS).concat(Constants.GET_CREATEDAT_FILTER)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT).concat(Constants.PEEK_METHOD)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(element_filter).concat(Constants.FLECHE).concat(element_filter).concat(Constants.POINT).concat(Constants.SET_VERSION)
                .concat(Constants.PARENTHESE_OUVRANTE).concat("i++").concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT)
                .concat(Constants.COLLECT_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.COLLECTORS_LIST).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        closeConditionAccolade(myWriter);
    }

    private void conditionIfretrievedOptionalEntityIsPresent(FileWriter myWriter, String retrievedEntity, String auditService, String viewFileDto, String entityMapper,
            String viewDtoVariable, String variableEntityMapper) throws IOException {
        String listOfretrievedVersions = "listOfretrievedVersions";
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(retrievedEntity).concat(Constants.POINT).concat(Constants.IS_PRESENT_METHOD)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        tabulation(myWriter);
        myWriter.write(AttributesTypeEnum.List.toString().concat(Constants.INFERIEUR).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(Constants.OBJECT)
                .concat(Constants.SUPERIEUR).concat(Constants.SUPERIEUR).concat(" ").concat(listOfretrievedVersions).concat(Constants.EGALE).concat(auditService)
                .concat(Constants.POINT).concat(Constants.METHOD_GET_VERSIONS_WITH_DEEP_PLUS).concat(Constants.PARENTHESE_OUVRANTE).concat(retrievedEntity).concat(Constants.POINT)
                .concat(Constants.GET_METHOD).concat(Constants.VIRGULE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        tabulation(myWriter);
        String element_forEach = "element";
        myWriter.write(listOfretrievedVersions.concat(Constants.POINT).concat(Constants.FOR_EACH).concat(Constants.PARENTHESE_OUVRANTE).concat(element_forEach)
                .concat(Constants.FLECHE).concat(Constants.ACCOLADE_OUVRANT));
        tabulation(myWriter);
        myWriter.write(Constants.TRY.concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        tabulation(myWriter);
        tabulation(myWriter);
        String mapToVersionDto = "mapToVersionDto";
        myWriter.write(Constants.VERSION_DTO.concat(Constants.INFERIEUR).concat(viewFileDto).concat(Constants.SUPERIEUR).concat(" ").concat(mapToVersionDto).concat(Constants.EGALE)
                .concat(variableEntityMapper).concat(Constants.POINT).concat(Constants.MAP_TO_VERSION_DTO).concat(Constants.PARENTHESE_OUVRANTE).concat(element_forEach)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        tabulation(myWriter);
        myWriter.write(viewDtoVariable.concat(Constants.POINT).concat(Constants.ADD).concat(Constants.PARENTHESE_OUVRANTE).concat(mapToVersionDto)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        closeConditionAccolade(myWriter);
        myWriter.write(Constants.CATCH_EXCEPTION);
        retourAlaLigneAndTabulation(myWriter);
        closeForEach(myWriter);
    }

    private String retrieveOptionalEntityFromMethodFindById(FileWriter myWriter, BuisnessLogicEntity ent, String serviceVariable) throws IOException {
        String retrievedEntity = ent.getNameEntity().toLowerCase();
        myWriter.write(Constants.OPTIONAL.concat(Constants.INFERIEUR).concat(ent.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(retrievedEntity)
                .concat(Constants.EGALE).concat(serviceVariable).concat(Constants.POINT).concat(Constants.FIND_BY_ID).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        return retrievedEntity;
    }

    private String declareVariableToretrieveListOfAllVersionsOfEntity(FileWriter myWriter, String viewFileDto, String viewFileDtoVariable) throws IOException {
        myWriter.write(AttributesTypeEnum.List.toString().concat(Constants.INFERIEUR).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(viewFileDto)
                .concat(Constants.SUPERIEUR).concat(Constants.SUPERIEUR).concat(" ").concat(viewFileDtoVariable).concat(Constants.EGALE).concat(Constants.NEW)
                .concat(Constants.ARRAY_LIST).concat(Constants.INFERIEUR).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(viewFileDto).concat(Constants.SUPERIEUR)
                .concat(Constants.SUPERIEUR).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        return viewFileDtoVariable;
    }

    private String initializeCounter(FileWriter myWriter) throws IOException {
        String counter = "i";
        myWriter.write(counter.concat(Constants.EGALE).concat("1").concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        return counter;
    }

    private void writeSignatureGetSortedVersionsMethod(FileWriter myWriter, String viewFileDto) throws IOException {
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(Constants.PUBLIC.concat(AttributesTypeEnum.List.toString()).concat(Constants.INFERIEUR).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR)
                .concat(viewFileDto).concat(Constants.SUPERIEUR).concat(Constants.SUPERIEUR).concat(" ").concat(Constants.METHOD_GET_SORTED_VERSIONS)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.LONG_LOWERCASE).concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void writeMethodCompareTwoVersionsOfTheSameObject(BuisnessLogicEntity ent, FileWriter myWriter, List<String> variables, String viewFileDto) throws IOException {
        writeSignatureMethod(myWriter);
        String retrievedListDiffEntities = "diff1";
        String retrievedListDiffVersions = "diff2";
        tabulation(myWriter);
        String retrieveVersions = retrieveVersionsFromGetSortedVersions(myWriter, viewFileDto);
        forEachAllRetrievedVersions(ent, myWriter, retrieveVersions, variables);
        retrieveListOfTheChangesOftheTwoObjects(myWriter, variables, retrievedListDiffEntities, retrievedListDiffVersions);
        String ListOfChanges = "diff";
        addTheTwoListOfChangesInOneList(myWriter, retrievedListDiffEntities, retrievedListDiffVersions, ListOfChanges);
        returnResult(myWriter, ListOfChanges);
        closeConditionAccolade(myWriter);
    }

    private void addTheTwoListOfChangesInOneList(FileWriter myWriter, String retrievedListDiffEntities, String retrievedListDiffVersions, String ListOfChanges) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION.concat(AttributesTypeEnum.List.toString()).concat(Constants.INFERIEUR).concat(Constants.VERSION_DIFF_DTO)
                .concat(Constants.SUPERIEUR).concat(" ").concat(ListOfChanges).concat(Constants.EGALE).concat(Constants.NEW).concat(Constants.ARRAY_LIST)
                .concat(Constants.INFERIEUR).concat(Constants.SUPERIEUR).concat(Constants.PARENTHESE_OUVRANTE).concat(retrievedListDiffEntities)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        myWriter.write(Constants.PATTERN_TABULATION.concat(ListOfChanges).concat(Constants.POINT).concat(Constants.ADD_ALL_METHOD).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(retrievedListDiffVersions).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void retrieveListOfTheChangesOftheTwoObjects(FileWriter myWriter, List<String> variables, String retrievedListDiffEntities, String retrievedListDiffVersions)
            throws IOException {
        retourAlaLigneAndTabulation(myWriter);

        retrievedChangesInList(myWriter, variables.get(0), variables.get(1), retrievedListDiffEntities);
        tabulation(myWriter);
        retrievedChangesInList(myWriter, variables.get(2), variables.get(3), retrievedListDiffVersions);

    }

    private void retrievedChangesInList(FileWriter myWriter, String var1, String var2, String retrievedListDiffEntities) throws IOException {
        myWriter.write(AttributesTypeEnum.List.toString().concat(Constants.INFERIEUR).concat(Constants.VERSION_DIFF_DTO).concat(Constants.SUPERIEUR).concat(" ")
                .concat(retrievedListDiffEntities).concat(Constants.EGALE).concat(Constants.AUDIT_SERVICE_POINT_COMPARE_TWO_OBJECTS).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(var1).concat(Constants.VIRGULE).concat(var2).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private String forEachAllRetrievedVersions(BuisnessLogicEntity ent, FileWriter myWriter, String retrieveVersions, List<String> variables) throws IOException {
        String forEach_element = "element";
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(retrieveVersions.concat(Constants.POINT).concat(Constants.FOR_EACH).concat(Constants.PARENTHESE_OUVRANTE).concat(forEach_element).concat(Constants.FLECHE)
                .concat(Constants.ACCOLADE_OUVRANT));
        retourAlaLigneAndTabulation(myWriter);
        conditionIfEntryVersionEgaleRetrievedVersion(ent, myWriter, variables.get(0), variables.get(1), variables.get(2), variables.get(3), forEach_element);
        return forEach_element;
    }

    private void conditionIfEntryVersionEgaleRetrievedVersion(BuisnessLogicEntity ent, FileWriter myWriter, String entity1, String entity2, String version1, String version2,
            String forEach_element) throws IOException {
        String v1 = "v1";
        String v2 = "v2";
        String methodGetEntityFromAllVersions = "get".concat(ent.getNameEntity()).concat("FromAllVersions");
        retourAlaLigneAndTabulation(myWriter);
        ifEntryVersionEgaleRetrievedVersion(myWriter, forEach_element, v1);
        retourAlaLigneAndTabulation(myWriter);
        retrieveEntityVariable(myWriter, entity1, forEach_element, methodGetEntityFromAllVersions);
        tabulation(myWriter);
        retrieveVersionVariable(myWriter, version1, forEach_element);
        closeConditionAccolade(myWriter);
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(Constants.ELSE.concat(" "));
        ifEntryVersionEgaleRetrievedVersion(myWriter, forEach_element, v2);
        retourAlaLigneAndTabulation(myWriter);
        retrieveEntityVariable(myWriter, entity2, forEach_element, methodGetEntityFromAllVersions);
        tabulation(myWriter);
        retrieveVersionVariable(myWriter, version2, forEach_element);
        closeConditionAccolade(myWriter);
        closeForEach(myWriter);
    }

    private void closeForEach(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION).concat(Constants.CLOSE_FOREACH).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void ifEntryVersionEgaleRetrievedVersion(FileWriter myWriter, String forEach_element, String v1) throws IOException {
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE)
                .concat(v1.concat(Constants.EGALE_CONDITION).concat(forEach_element).concat(Constants.POINT).concat(Constants.GET_VERSION)).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void closeConditionAccolade(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.ACCOLADE_FERMANTE));
    }

    private void retrieveVersionVariable(FileWriter myWriter, String version1, String forEach_element) throws IOException {
        myWriter.write(version1.concat(Constants.EGALE).concat(Constants.METHOD_GET_VERSION_FROM_ALL_VERSIONS).concat(Constants.PARENTHESE_OUVRANTE).concat(forEach_element)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void retrieveEntityVariable(FileWriter myWriter, String entity1, String forEach_element, String methodGetEntityFromAllVersions) throws IOException {
        myWriter.write(entity1.concat(Constants.EGALE).concat(methodGetEntityFromAllVersions).concat(Constants.PARENTHESE_OUVRANTE).concat(forEach_element)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private String retrieveVersionsFromGetSortedVersions(FileWriter myWriter, String viewFileDto) throws IOException {
        String retrieveVersions = "versions";
        myWriter.write(AttributesTypeEnum.List.toString().concat(Constants.INFERIEUR).concat(Constants.VERSION_DTO).concat(Constants.INFERIEUR).concat(viewFileDto)
                .concat(Constants.SUPERIEUR).concat(Constants.SUPERIEUR).concat(" ").concat(retrieveVersions).concat(Constants.EGALE).concat(Constants.METHOD_GET_SORTED_VERSIONS)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        return retrieveVersions;
    }

    private void writeSignatureMethod(FileWriter myWriter) throws IOException {
        String v1 = "v1";
        String v2 = "v2";
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(Constants.PUBLIC.concat(AttributesTypeEnum.List.toString()).concat(Constants.INFERIEUR).concat(Constants.VERSION_DIFF_DTO).concat(Constants.SUPERIEUR)
                .concat(" ").concat(Constants.METHOD_COMPARE_TWO_OBJECTS).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.LONG_LOWERCASE).concat(" ")
                .concat(Constants.ID_MINUS).concat(Constants.VIRGULE).concat(Constants.INT).concat(" ").concat(v1).concat(Constants.VIRGULE).concat(Constants.INT).concat(" ")
                .concat(v2).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void returnResult(FileWriter myWriter, String result) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RETURN.concat(" ").concat(result).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

    }

    private String firstLetterUpperCase(Attribute attributeForGetterAndSetter) {
        String firstLetter = attributeForGetterAndSetter.getNameAttribute().charAt(0) + "";
        String getterAttribute = firstLetter.toUpperCase().concat(attributeForGetterAndSetter.getNameAttribute().substring(1));
        return getterAttribute;
    }

    private boolean isThisMasterEntityAndOneToManyRelationship(String nameEntity, List<Relationship> relations) {
        Boolean b = false;
        for (Relationship relationship : relations) {
            if (relationship.getMasterEntity().getNameEntity().equals(nameEntity) && relationship.getTypeRelationship().equals(RelationshipTypeEnum.OneToMany)) {
                b = true;
            }
        }
        return b;

    }

    private String existEntityInMasterTableOneToMany(String nameEntity, List<Relationship> relations) {
        String slaverEntityName = null;
        for (Relationship relationship : relations) {
            if (relationship.getMasterEntity().getNameEntity().equals(nameEntity) && relationship.getTypeRelationship().equals(RelationshipTypeEnum.OneToMany)) {
                if (relationship.getSlaveEntity().getNameEntity() != null) {
                    slaverEntityName = relationship.getSlaveEntity().getNameEntity();
                    System.out.println(slaverEntityName);
                }
            }
        }
        return slaverEntityName;

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

    private void writeViewImportsAfterAddRelationshipsAttribute(BuisnessLogicEntity ent, Project project) throws IOException {
        String slave = existEntityInMasterTableOneToMany(ent.getNameEntity(), ent.getRelationshipsMaster());
        String namefileToWriteIn = ent.getNameEntity().concat("Audit").concat("Service");
        if (slave != null) {
            String childModel = slave;
            File file = new File(
                    ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_AUDIT_SERVICE).concat(namefileToWriteIn).concat(".java"));
            String fileContext = FileUtils.readFileToString(file);
            fileContext = fileContext.replaceAll(ConstantsImportPackage.IMPORT_VERSION_DIFF_DTO, ConstantsImportPackage.IMPORT_VERSION_DIFF_DTO
                    .concat(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(childModel).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)));
            FileUtils.write(file, fileContext);
        }

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
        tabulation(myWriter);
        myWriter.write(entityMapper.concat(" ").concat(variableEntityMapper).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void autoWiredEntityService(FileWriter myWriter, String serviceFile, String serviceVariable) throws IOException {
        autoWiredAnnotation(myWriter);
        tabulation(myWriter);
        myWriter.write(serviceFile.concat(" ").concat(serviceVariable).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void autoWiredAuditService(FileWriter myWriter, String auditServiceFile, String auditServiceVariable) throws IOException {
        autoWiredAnnotation(myWriter);
        tabulation(myWriter);
        myWriter.write(auditServiceFile.concat(" ").concat(auditServiceVariable).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void retourAlaLigneAndTabulation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
    }

    private void tabulation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
    }

    private void autoWiredAnnotation(FileWriter myWriter) throws IOException {
        retourAlaLigneAndTabulation(myWriter);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
    }

    private FileWriter writeImportsAndStructureOfClassInAuditServiceFiles(Project project, BuisnessLogicEntity ent, String nameFileAuditService, String serviceFile,
            String viewFileDto) throws IOException {

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
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(viewFileDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
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

    private void injectChildMapper(BuisnessLogicEntity ent, Project project, String entityMapper, String variableEntityMapper) throws IOException {
        String slave = existEntityInMasterTableOneToMany(ent.getNameEntity(), ent.getRelationshipsMaster());
        String namefileToWriteIn = ent.getNameEntity().concat("Audit").concat("Service");
        if (slave != null) {
            String childMapper = slave.concat("Mapper");
            String childMapperVariable = slave.toLowerCase().concat("Mapper");
            File file = new File(
                    ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_AUDIT_SERVICE).concat(namefileToWriteIn).concat(".java"));
            String fileContext = FileUtils.readFileToString(file);
            fileContext = fileContext.replaceAll(entityMapper.concat(" ").concat(variableEntityMapper).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE),
                    entityMapper.concat(" ").concat(variableEntityMapper).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_RETOUR_LIGNE)
                            .concat(Constants.PATTERN_TABULATION).concat(ConstantsAnnotations.ANNOTATION_AUTOWIRED).concat(Constants.PATTERN_TABULATION).concat(childMapper)
                            .concat(" ").concat(childMapperVariable).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
            FileUtils.write(file, fileContext);
        }
    }

    private void importInjectedChildMapper(BuisnessLogicEntity ent, Project project, String entityMapper, String variableEntityMapper) throws IOException {
        String slave = existEntityInMasterTableOneToMany(ent.getNameEntity(), ent.getRelationshipsMaster());
        String namefileToWriteIn = ent.getNameEntity().concat("Audit").concat("Service");
        if (slave != null) {
            String childModel = slave;
            String childMapperImport = slave.concat("Mapper");
            String modelChildImport = ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(childModel).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
            String mapperChildImport = ConstantsImportPackage.IMPORT_MAPPER.concat(childMapperImport).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE);
            File file = new File(
                    ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_AUDIT_SERVICE).concat(namefileToWriteIn).concat(".java"));
            String fileContext = FileUtils.readFileToString(file);
            fileContext = fileContext.replaceAll(modelChildImport, modelChildImport.concat(mapperChildImport));
            FileUtils.write(file, fileContext);
        }
    }

}
