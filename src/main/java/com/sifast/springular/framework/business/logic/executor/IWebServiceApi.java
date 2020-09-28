package com.sifast.springular.framework.business.logic.executor;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsAnnotations;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsImportPackage;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IWebServiceApi {

    @Value("${path.generated-project}")
    private String pathToGeneratedProject;

    public void generateIWebServiceApiFiles(BuisnessLogicEntity ent, Project project) {
        try {
            FileWriter myWriter = writeImportsAndStructureOfClassInApis(project, ent);
            writeAnnotationsAndSignature(ent, myWriter);
            closeAccoladeAndFile(myWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private FileWriter writeImportsAndStructureOfClassInApis(Project project, BuisnessLogicEntity ent) throws IOException {
        String fileInterfaceApi = "I".concat(ent.getNameEntity()).concat("Api");
        File file = new File(pathToGeneratedProject.concat(project.getNameProject()).concat(ConstantsPath.PATH_TO_PROJECT_IWEBSERVICEAPI).concat(fileInterfaceApi).concat(".java"));
        String fileCreateDto = ent.getNameEntity().concat("Dto");
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = ent.getNameEntity().concat("Dto");

        FileWriter myWriter = new FileWriter(file);
        myWriter.write("");
        myWriter.write(ConstantsImportPackage.PACKAGE_WEB_SERVICE_API_INTERFACE.concat(Constants.PATTERN_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_RESPONSE_ENTITY);
        myWriter.write(ConstantsImportPackage.IMPORT_BINDING_RESULT);
        myWriter.write(ConstantsImportPackage.IMPORT_GET_MAPPING);
        myWriter.write(ConstantsImportPackage.IMPORT_POST_MAPPING);
        myWriter.write(ConstantsImportPackage.IMPORT_PUT_MAPPING);
        myWriter.write(ConstantsImportPackage.IMPORT_DELETE_MAPPING);

        myWriter.write(ConstantsImportPackage.IMPORT_API_MESSAGE);
        myWriter.write(ConstantsImportPackage.IMPORT_API_STATUS);
        myWriter.write(ConstantsImportPackage.IMPORT_HTTP_ERROR_MESSAGE);

        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileCreateDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileViewDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
        myWriter.write(ConstantsImportPackage.IMPORT_DTO.concat(ent.getNameEntity().toLowerCase()).concat(Constants.POINT).concat(fileDto)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

        myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API_OPERATION);
        myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API_RESPONSE);
        myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API_RESPONSES);
        myWriter.write(ConstantsImportPackage.IMPORT_SAWAGGER_AUTHORIZATION);
        myWriter.write(ConstantsImportPackage.IMPORT_SAWAGGER_AUTHORIZATION_SCOPE);

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PUBLIC_INTERFACE.concat(fileInterfaceApi).concat(Constants.PATTERN_RETOUR_LIGNE).concat(Constants.ACCOLADE_OUVRANT)
                .concat(Constants.PATTERN_RETOUR_LIGNE));
        return myWriter;
    }

    private void writeAnnotationsAndSignature(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
        String fileCreateDto = ent.getNameEntity().concat("Dto");
        String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
        String fileDto = ent.getNameEntity().concat("Dto");
        signatureSaveApi(ent, myWriter, fileCreateDto, fileViewDto);
        signatureGetApi(ent, myWriter, fileViewDto);
        signatureGetAllApi(ent, myWriter, fileViewDto);
        signatureDeleteApi(ent, myWriter, fileViewDto);
        signatureUpdateApi(ent, myWriter, fileDto, fileViewDto);
    }

    private void signatureUpdateApi(BuisnessLogicEntity ent, FileWriter myWriter, String fileDto, String fileViewDto) throws IOException {

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_OPERATION.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("update ")
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE).concat(fileViewDto)
                .concat(Constants.POINT_CLASS).concat(Constants.VIRGULE).concat(Constants.SWAGGER_API_OPERATION_AUTHORIZATION_SCOPE_READ).concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.PUT_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_SLASH)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.ID_MINUS)
                .concat(Constants.ACCOLADE_FERMANTE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RESPONSE_ENTITY.concat("update").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(AttributesTypeEnum.Long.toString().toLowerCase()).concat(" ").concat(Constants.ID_MINUS).concat(Constants.VIRGULE).concat(fileDto).concat(" ")
                .concat(fileDto.toLowerCase()).concat(Constants.VIRGULE).concat(Constants.BINDING_RESULT).concat(" ").concat(Constants.BINDING_RESULT.toLowerCase())
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void signatureDeleteApi(BuisnessLogicEntity ent, FileWriter myWriter, String fileViewDto) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_OPERATION.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("delete ")
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE).concat(fileViewDto)
                .concat(Constants.POINT_CLASS).concat(Constants.VIRGULE).concat(Constants.SWAGGER_API_OPERATION_AUTHORIZATION_SCOPE_READ).concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.DELETE_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_SLASH)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.ID_MINUS)
                .concat(Constants.ACCOLADE_FERMANTE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RESPONSE_ENTITY.concat("delete").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE)
                .concat(AttributesTypeEnum.Long.toString().toLowerCase()).concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE)
                .concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void signatureGetAllApi(BuisnessLogicEntity ent, FileWriter myWriter, String fileViewDto) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_OPERATION.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("getAll ")
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE).concat(fileViewDto)
                .concat(Constants.POINT_CLASS).concat(Constants.VIRGULE).concat(Constants.SWAGGER_API_OPERATION_AUTHORIZATION_SCOPE_READ).concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.GET_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_SLASH)
                .concat(ent.getNameEntity().toLowerCase()).concat("s").concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RESPONSE_ENTITY.concat("getAll").concat(ent.getNameEntity()).concat("s").concat(Constants.PARENTHESE_OUVRANTE)
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void signatureGetApi(BuisnessLogicEntity ent, FileWriter myWriter, String fileViewDto) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_OPERATION.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("get ")
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE).concat(fileViewDto)
                .concat(Constants.POINT_CLASS).concat(Constants.VIRGULE).concat(Constants.SWAGGER_API_OPERATION_AUTHORIZATION_SCOPE_READ).concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.GET_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_SLASH)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.PATTERN_SLASH).concat(Constants.ACCOLADE_OUVRANT).concat(Constants.ID_MINUS)
                .concat(Constants.ACCOLADE_FERMANTE).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(
                Constants.RESPONSE_ENTITY.concat("get").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(AttributesTypeEnum.Long.toString().toLowerCase())
                        .concat(" ").concat(Constants.ID_MINUS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));

    }

    private void signatureSaveApi(BuisnessLogicEntity ent, FileWriter myWriter, String fileCreateDto, String fileViewDto) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_RESPONSES.concat(Constants.PARENTHESE_VALUE_EGALE_ACCOLADE));

        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_RESPONSE.concat(Constants.PARENTHESE_CODE_EGALE).concat(Constants.API_STATUS_ACCEPTED).concat(Constants.VIRGULE)
                .concat(Constants.API_MESSAGE).concat(ent.getNameEntity().toUpperCase()).concat(Constants.CREATED_SUCCESSFULLY).concat(Constants.VIRGULE)
                .concat(Constants.RESPONSE_EGALE).concat(fileViewDto).concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.VIRGULE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_RESPONSE.concat(Constants.PARENTHESE_CODE_EGALE).concat(Constants.API_STATUS_BAD_REQUEST)
                .concat(Constants.VIRGULE).concat(Constants.API_MESSAGE).concat(Constants.INVALID_INPUT).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE)
                .concat(Constants.HTTP_ERROR_RESPONSE).concat(Constants.POINT_CLASS).concat(Constants.PARENTHESE_FERMANTE).concat(Constants.ACCOLADE_FERMANTE)
                .concat(Constants.PARENTHESE_FERMANTE));
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);

        myWriter.write(ConstantsAnnotations.ANNOTATION_SWAGGER_API_OPERATION.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat("save ")
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.VIRGULE).concat(Constants.RESPONSE_EGALE).concat(fileViewDto)
                .concat(Constants.POINT_CLASS).concat(Constants.VIRGULE).concat(Constants.SWAGGER_API_OPERATION_AUTHORIZATION_SCOPE_READ).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(ConstantsAnnotations.POST_MAPPING.concat(Constants.PARENTHESE_VALUE_EGALE).concat(Constants.DOUBLE_COTE).concat(Constants.PATTERN_SLASH)
                .concat(ent.getNameEntity().toLowerCase()).concat(Constants.DOUBLE_COTE).concat(Constants.PARENTHESE_FERMANTE));

        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        String paramFileDto = ent.getNameEntity().toLowerCase().concat("Dto");
        myWriter.write(Constants.PATTERN_TABULATION);
        myWriter.write(Constants.RESPONSE_ENTITY.concat("save").concat(ent.getNameEntity()).concat(Constants.PARENTHESE_OUVRANTE).concat(fileCreateDto).concat(" ")
                .concat(paramFileDto).concat(Constants.VIRGULE).concat(Constants.BINDING_RESULT).concat(" ").concat(Constants.BINDING_RESULT.toLowerCase())
                .concat(Constants.PARENTHESE_FERMANTE).concat(Constants.PATTERN_POINT_VIRGULE_ET_RETOUR_LIGNE));
    }

    private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
        myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
        myWriter.write(Constants.ACCOLADE_FERMANTE);
        myWriter.close();
    }
}
