package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class WebServiceApiImpl {

    public void generateWebServicesImplFiles(BuisnessLogicEntity entity, Project project) {
        project.getEntities().stream().forEach(ent -> {
            try {
                FileWriter myWriter = writeImportsAndStructureOfClassInWSApi(project, ent);
                injectServicesAndConfigInWS(ent, myWriter);
                writeWebServicesMethods(ent, myWriter);
                closeAccoladeAndFile(myWriter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private FileWriter writeImportsAndStructureOfClassInWSApi(Project project, BuisnessLogicEntity ent) throws IOException {
        File file = new File(ConstantsPath.DESKTOP.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_WS_API).concat(ent.getNameEntity()).concat("Api.java"));
        String fileApi = ent.getNameEntity().concat("Api");
        String fileCreateDto = "Create".concat(ent.getNameEntity()).concat("Dto");
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = ent.getNameEntity().concat("Dto");
        String interfaceService = "I".concat(ent.getNameEntity()).concat("Service");
        String entityMApper = ent.getNameEntity().concat("Mapper");
        String interfaceApi = "I".concat(ent.getNameEntity()).concat("Api");

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_WEB_SERVICE_API_IMPL.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_COLLECTIONS);
        myWriter.write(ConstantsImportPackage.IMPORT_LIST);
        myWriter.write(ConstantsImportPackage.IMPORT_OPTIONAL);
        myWriter.write(ConstantsImportPackage.IMPORT_COLLECTORS);

        myWriter.write(ConstantsImportPackage.IMPORT_LOGGER);
        myWriter.write(ConstantsImportPackage.IMPORT_LOGGER_FACTORY);
        myWriter.write(ConstantsImportPackage.IMPORT_AUTOWIRED);
        myWriter.write(ConstantsImportPackage.IMPORT_HTTP_STATUS);
        myWriter.write(ConstantsImportPackage.IMPORT_RESPONSE_ENTITY);
        myWriter.write(ConstantsImportPackage.IMPORT_BINDING_RESULT);
        myWriter.write(ConstantsImportPackage.IMPORT_CROSS_ORIGIN);
        myWriter.write(ConstantsImportPackage.IMPORT_PATH_VARIABLE);
        myWriter.write(ConstantsImportPackage.IMPORT_REQUEST_BODY);
        myWriter.write(ConstantsImportPackage.IMPORT_REQUEST_MAPPING);
        myWriter.write(ConstantsImportPackage.IMPORT_REST_CONTROLLER);

        myWriter.write(ConstantsImportPackage.IMPORT_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_HTTP_COSTUM_CODE);
        myWriter.write(ConstantsImportPackage.IMPORT_HTTP_ERROR_RESPONSE);
        myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(ent.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE.concat(interfaceService).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_CONFIGURE_MODEL_MAPPER);
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileCreateDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileViewDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileDto)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL.concat(ent.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_MAPPER.concat(entityMApper).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_IAPI.concat(interfaceApi).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_VALIDATOR.concat(ent.getNameEntity()).concat("Validator").concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_IWEBSERVICES_VALIDATORS);

        myWriter.write(ConstantsImportPackage.IMPORT_ANNOTATION_VALIDATED);

        myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API);
        myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API_PARAM);

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_REST_CONTROLLER);

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_CROSS_ORIGIN);

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_API.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(ent.getNameEntity().toLowerCase())
                .concat(" api").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(" tags").concat(Constants.EGALE).concat(Constants.DOUBLE_COTE)
                .concat(ent.getNameEntity().toLowerCase()).concat("-api").concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(ConstantsAnnotations.ANNOTATION_REQUEST_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.SLASH_API)
                .concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_CLASS.concat(fileApi).concat(" ").concat(Constants.IMPLEMENTS).concat(" ").concat(interfaceApi).concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;

    }

    private void injectServicesAndConfigInWS(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        loggerDeclaration(ent, myWriter);

        httpErrorResponseDeclaration(myWriter);

        httpResponseBodyDeclaration(myWriter);

        httpStatusDeclaration(myWriter);

        injectModelMapper(myWriter);

        injectEntityMapper(ent, myWriter);

        injectInterfaceService(ent, myWriter);

        injectValidators(ent, myWriter);
    }

    private void injectValidators(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        String fileValidator = ent.getNameEntity().concat("Validator");
        String variableValidator = ent.getNameEntity().toLowerCase().concat("Validator");
        myWriter.write(Constants.PRIVATE.concat(fileValidator).concat(" ").concat(variableValidator).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

    }

    private void writeWebServicesMethods(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String entities = ent.getNameEntity().toLowerCase().concat("s");
        String variableService = ent.getNameEntity().toLowerCase().concat("Service");
        getAllWS(ent, myWriter, entities, variableService);
        deleteWS(ent, myWriter, variableService);
        getByIdWS(ent, myWriter, variableService);
        saveWs(ent, myWriter, variableService);

    }

    private void saveWs(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        overrideAnnotation(myWriter);
        signatureOfSaveMethod(ent, myWriter);
        implementationOfSaveMethod(ent, myWriter, variableService);
    }

    private void signatureOfSaveMethod(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PUBLIC.concat(Constants.RESPONSE_ENTITY).concat("save").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(ConstantsAnnotations.ANNOTATION_API_PARAM)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.REQUIRED_EGALE_TRUE).concat(Constants.VIRGULE).concat(Constants.VALUE_EGALE).concat(Constants.DOUBLE_COTE)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.NAME_EGALE).concat(Constants.DOUBLE_COTE)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ")
                .concat(ConstantsAnnotations.ANNOTATION_REQUEST_BODY).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(ConstantsAnnotations.ANNOTATION_VALIDATED).concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.ACCOLADE_OUVRANT)
                .concat("IWebServicesValidators.class").concat(Constants.ACCOLADE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(" Create").concat(ent.getNameEntity())
                .concat("Dto").concat(" ").concat(ent.getNameEntity().toLowerCase()).concat("Dto").concat(Constants.VIRGULE).concat(" ").concat(Constants.BINDING_RESULT)
                .concat(" ").concat(Constants.BINDING_RESULT.toLowerCase()).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void implementationOfSaveMethod(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        loggerInfoSave(ent, myWriter);
        trySave(ent, myWriter, variableService);
        catchSave(ent, myWriter);
        returnWS(myWriter);
    }

    private void trySave(BuisnessLogicEntity ent, FileWriter myWriter, String lowerCase) throws IOException {
        openTryMethod(myWriter);
        validateInputData(ent, myWriter);
        String entityToSave = entityToSave(ent, myWriter);
        String savedEntity = savedEntity(ent, myWriter, entityToSave);
        httpStatusCreated(myWriter);
        saveHttpResponseBody(ent, myWriter, savedEntity);
        saveLoggerInfo(ent, myWriter, savedEntity);
        closeTryMethod(myWriter);
    }

    private void closeTryMethod(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
    }

    private void saveLoggerInfo(BuisnessLogicEntity ent, FileWriter myWriter, String savedEntity) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.LOGGER_INFO.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat("INFO level message: ").concat(ent.getNameEntity())
                .concat(" saved{}").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(savedEntity).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void saveHttpResponseBody(BuisnessLogicEntity ent, FileWriter myWriter, String savedEntity) throws IOException {
        String variableMapper = ent.getNameEntity().toLowerCase().concat("Mapper");
        String viewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.VARIABLE_HTTP_RESPONSE_BODY.concat(Constants.EGALE).concat(variableMapper).concat(Constants.POINT).concat("map").concat(ent.getNameEntity())
                .concat("To").concat(viewDto).concat(Constants.PARENTHESE_OUVRANTE).concat(savedEntity).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void httpStatusCreated(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_STATUS_CREATED);
    }

    private String savedEntity(BuisnessLogicEntity ent, FileWriter myWriter, String entityToSave) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        String savedEntity = "saved".concat(ent.getNameEntity());
        String variableService = ent.getNameEntity().toLowerCase().concat("Service");
        myWriter.write(ent.getNameEntity().concat(" ").concat(savedEntity).concat(Constants.EGALE).concat(variableService).concat(Constants.POINT).concat("save")
                .concat(Constants.PARENTHESE_OUVRANTE).concat(entityToSave).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)

        );
        return savedEntity;
    }

    private String entityToSave(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        String entityToSave = ent.getNameEntity().substring(0, 1).toLowerCase().concat(ent.getNameEntity().substring(1, ent.getNameEntity().length())).concat("ToSave");
        String variableMapper = ent.getNameEntity().toLowerCase().concat("Mapper");

        myWriter.write(ent.getNameEntity().concat(" ").concat(entityToSave).concat(Constants.EGALE).concat(variableMapper).concat(Constants.POINT).concat("map")
                .concat(ent.getNameEntity()).concat("Dto").concat("To").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(ent.getNameEntity().toLowerCase().concat("Dto")).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
        return entityToSave;
    }

    private void openTryMethod(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        myWriter.write(Constants.TRY);
        myWriter.write(Constants.ACCOLADE_OUVRANT);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void validateInputData(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(
                ent.getNameEntity().toLowerCase().concat("Validator").concat(Constants.POINT).concat(Constants.METHOD_VALIDATE_INPUT_DATA).concat(Constants.PARENTHESE_OUVRANTE)
                        .concat(Constants.BINDING_RESULT.toLowerCase()).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void catchSave(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        catchMethod(myWriter);

        httpErrorResponseSet(ent, myWriter);

        httpResponseBody(myWriter);

        httpStatusInternalServerError(myWriter);

        catchLoggerError(myWriter);

        closeCatch(myWriter);

    }

    private void closeCatch(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void catchLoggerError(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.CATCH_LOGGER_ERROR);
    }

    private void httpStatusInternalServerError(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_STATUS_INTERNAL_SERVER_ERROR);
    }

    private void httpResponseBody(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_RESPONSE_BODY_EGALE_HTTP_ERROR_RESPONSE);
    }

    private void httpErrorResponseSet(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_ERROR_RESPONSE_SET_CODE_AND_MESSAGE.concat(Constants.ERROR_SAVE_).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void catchMethod(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        myWriter.write(Constants.CATCH);
        myWriter.write(Constants.PARENTHESE_OUVRANTE);
        myWriter.write(Constants.DECLARATION_EXCEPTION_EX_IN_CATCH);
        myWriter.write(Constants.PARENTHESE_FERMANTE);
        myWriter.write(Constants.ACCOLADE_OUVRANT);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
    }

    private void loggerInfoSave(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.LOGGER_INFO.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat("Web Service save ").concat(ent.getNameEntity())
                .concat(" invoked with args {}").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(ent.getNameEntity().toLowerCase()).concat("Dto")
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE));

    }

    private void getByIdWS(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        overrideAnnotation(myWriter);
        signatureOfGetByIdMethod(ent, myWriter);
        implementationOfGetByIdMethod(ent, myWriter, variableService);
    }

    private void signatureOfGetByIdMethod(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PUBLIC.concat(Constants.RESPONSE_ENTITY).concat("get").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(ConstantsAnnotations.ANNOTATION_API_PARAM)
                .concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("ID of ").concat(ent.getNameEntity()).concat(" that needs to be fetched")
                .concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.REQUIRED_EGALE_TRUE).concat(Constants.VIRGULE).concat(Constants.ALLOWABLE_VALUES)
                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(ConstantsAnnotations.PATH_VARIABLE).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.ID_MINUS).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(AttributesTypeEnum.Long.toString().toLowerCase())
                .concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));

    }

    private void implementationOfGetByIdMethod(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        loggerInfoGetById(ent, myWriter);
        String entityToBefinded = ent.getNameEntity().toLowerCase();
        optionalObject(ent, myWriter, variableService, entityToBefinded);
        ifOptionalObjectIsPresentGetById(ent, myWriter, entityToBefinded, variableService);
        elseOptionalObjectIsPresentGetById(ent, myWriter);
        returnWS(myWriter);

    }

    private void loggerInfoGetById(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.LOGGER_INFO.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat("Web Service get").concat(ent.getNameEntity())
                .concat(" invoked with id {}").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE));
    }

    private void ifOptionalObjectIsPresentGetById(BuisnessLogicEntity ent, FileWriter myWriter, String entityToBefinded, String variableService) throws IOException {

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        String entityMapper = ent.getNameEntity().toLowerCase().concat("Mapper");
        String mapEntityToViewEntityDto = "map".concat(ent.getNameEntity()).concat("ToView").concat(ent.getNameEntity()).concat("Dto");
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(entityToBefinded).concat(Constants.POINT).concat(Constants.IS_PRESENT_METHOD)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_STATUS_OK)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_RESPONSE_BODY).concat(Constants.EGALE).concat(entityMapper)
                .concat(Constants.POINT).concat(mapEntityToViewEntityDto).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT)
                .concat(Constants.GET_METHOD).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.ACCOLADE_FERMANTE));
    }

    private void elseOptionalObjectIsPresentGetById(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.ELSE.concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.HTTP_ERROR_RESPONSE_SET_CODE_AND_MESSAGE).concat(ent.getNameEntity().toUpperCase()).concat(Constants._NOT_FOUND)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_STATUS_NOT_FOUND).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.HTTP_RESPONSE_BODY_EGALE_HTTP_ERROR_RESPONSE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE)

        );

    }

    private void deleteWS(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        overrideAnnotation(myWriter);
        signatureOfDeleteMethod(ent, myWriter);
        implementationOfDeleteMethod(ent, myWriter, variableService);

    }

    private void signatureOfDeleteMethod(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {

        myWriter.write(Constants.PUBLIC.concat(Constants.RESPONSE_ENTITY).concat("delete").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(ConstantsAnnotations.ANNOTATION_API_PARAM)
                .concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("ID of ").concat(ent.getNameEntity()).concat(" that needs to be deleted")
                .concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.REQUIRED_EGALE_TRUE).concat(Constants.VIRGULE).concat(Constants.ALLOWABLE_VALUES)
                .concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(ConstantsAnnotations.PATH_VARIABLE).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE)
                .concat(Constants.ID_MINUS).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE).concat(" ").concat(AttributesTypeEnum.Long.toString().toLowerCase())
                .concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
    }

    private void implementationOfDeleteMethod(BuisnessLogicEntity ent, FileWriter myWriter, String variableService) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION));
        loggerInfo(ent, myWriter);
        String preDeleteEntity = "preDelete".concat(ent.getNameEntity().toLowerCase());
        optionalObject(ent, myWriter, variableService, preDeleteEntity);
        ifOptionalObjectIsPresent(ent, myWriter, preDeleteEntity, variableService);
        elseOptionalObjectIsPresent(ent, myWriter);
        returnWS(myWriter);

    }

    private void ifOptionalObjectIsPresent(BuisnessLogicEntity ent, FileWriter myWriter, String preDeleteEntity, String variableService) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.IF.concat(Constants.PARENTHESE_OUVRANTE).concat(preDeleteEntity).concat(Constants.POINT).concat(Constants.IS_PRESENT_METHOD)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION).concat(variableService).concat(Constants.POINT)
                .concat(Constants.DELETE_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(preDeleteEntity).concat(Constants.POINT).concat(Constants.GET_METHOD)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_STATUS_OK).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.LOGGER_INFO).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat("INFO level message: ")
                .concat(ent.getNameEntity().toLowerCase()).concat("with id = {} deleted").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.ID_MINUS)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.ACCOLADE_FERMANTE));

    }

    private void elseOptionalObjectIsPresent(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.ELSE.concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.HTTP_ERROR_RESPONSE_SET_CODE_AND_MESSAGE).concat(ent.getNameEntity().toUpperCase()).concat(Constants._NOT_FOUND)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_STATUS_NOT_FOUND).concat(Constants.PATTERN_TABULATION).concat(Constants.PATTERN_TABULATION)
                .concat(Constants.HTTP_RESPONSE_BODY_EGALE_HTTP_ERROR_RESPONSE).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE)

        );

    }

    private void optionalObject(BuisnessLogicEntity ent, FileWriter myWriter, String variableService, String preDeleteEntity) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.OPTIONAL.concat(Constants.INFERIEUR).concat(ent.getNameEntity()).concat(Constants.SUPERIEUR).concat(" ").concat(preDeleteEntity)
                .concat(Constants.EGALE).concat(variableService).concat(Constants.POINT).concat(Constants.FIND_BY_ID).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void loggerInfo(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.LOGGER_INFO.concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.DOUBLE_COTE).concat("Web Service delete").concat(ent.getNameEntity())
                .concat(" invoked with id {}").concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE));
    }

    private void getAllWS(BuisnessLogicEntity ent, FileWriter myWriter, String entities, String variableService) throws IOException {
        overrideAnnotation(myWriter);
        signatureOfGetAllMethod(ent, myWriter);
        implementationOfGetAllMethod(ent, myWriter, entities, variableService);
    }

    private void implementationOfGetAllMethod(BuisnessLogicEntity ent, FileWriter myWriter, String entities, String variableService) throws IOException {
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE.concat(Constants.PATTERN_TABULATION).concat(Constants.TEST_LIST).concat(ent.getNameEntity()).concat(Constants.SUPERIEUR)
                .concat(" ").concat(entities).concat(Constants.EGALE).concat(variableService).concat(Constants.POINT).concat(Constants.FINDALL)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_STATUS_OK).concat(Constants.PATTERN_TABULATION).concat(Constants.HTTP_RESPONSE_BODY)
                .concat(Constants.EGALE).concat(Constants.NOT).concat(entities).concat(Constants.POINT).concat(Constants.IS_EMPTY).concat(Constants.PATTERN_RETOUR_LIGNE)
                .concat(Constants.PATTERN_TABULATION).concat(Constants.POINT_INTERROGATION).concat(entities).concat(Constants.POINT).concat(Constants.STREAM_METHOD)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.POINT).concat(Constants.MAP_METHOD)
                .concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat(Constants.FLECHE).concat(Constants.MODEL_MAPPER_VARIABLE)
                .concat(Constants.POINT).concat(Constants.MAP_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity().toLowerCase()).concat(Constants.VIRGULE)
                .concat(fileViewDto).concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.POINT)
                .concat(Constants.COLLECT_METHOD).concat(Constants.PARENTHESE_OUVRANTE).concat(Constants.COLLECTORS_LIST).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.PATTERN_TABULATION).concat(Constants.DEUX_POINTS).concat(Constants.COLLECTIONS_EMPTY_LIST)
                .concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE).concat(Constants.PATTERN_RETOUR_LIGNE));
        returnWS(myWriter);
    }

    private void returnWS(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_TABULATION.concat(Constants.RETURN_WS).concat(Constants.PATTERN_TABULATION).concat(Constants.ACCOLADE_FERMANTE));
    }

    private void signatureOfGetAllMethod(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PUBLIC.concat(Constants.RESPONSE_ENTITY).concat("getAll").concat(ent.getNameEntity()).concat("s").concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_OUVRANT));
    }

    private void overrideAnnotation(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_OVERRIDE);
        myWriter.write(Constants.PATTERN_TABULATION);
    }

    private void httpStatusDeclaration(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_HTTP_STATUS_DECLARATION);
    }

    private void httpResponseBodyDeclaration(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_RESPONSE_BODY_DECLARATION);
    }

    private void httpErrorResponseDeclaration(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.HTTP_ERROR_RESPONSE_DECLARATION);
    }

    private void loggerDeclaration(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.LOGGER.concat(Constants.PARENTHESE_OUVRANTE).concat(ent.getNameEntity()).concat("Api").concat(Constants.POINT_CLASS)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void injectModelMapper(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.INJECT_MODEL_MAPPER);
    }

    private void injectEntityMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        String fileMapper = ent.getNameEntity().concat("Mapper");
        String variableMapper = ent.getNameEntity().toLowerCase().concat("Mapper");
        myWriter.write(Constants.PRIVATE.concat(fileMapper).concat(" ").concat(variableMapper).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void injectInterfaceService(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
        myWriter.write(Constants.PATTERN_TABULATION);
        String fileService = "I".concat(ent.getNameEntity()).concat("Service");
        String variableService = ent.getNameEntity().toLowerCase().concat("Service");
        myWriter.write(Constants.PRIVATE.concat(fileService).concat(" ").concat(variableService).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();

    }

}
