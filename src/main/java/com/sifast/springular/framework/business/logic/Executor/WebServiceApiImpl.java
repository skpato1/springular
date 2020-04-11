package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

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
				FileWriter myWriter = writeImportsAndStructureOfClassInWSApi(ent);
				injectServicesAndConfigInWS(ent, myWriter);
				writeWebServicesMethods(ent, myWriter);
				closeAccoladeAndFile(myWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	

	private FileWriter writeImportsAndStructureOfClassInWSApi(BuisnessLogicEntity ent) throws IOException {
		File file = new File(ConstantsPath.PATH_TO_SPRINGULAR_WS_API
				.concat(ent.getNameEntity())
				.concat("Api.java"));
		String fileApi = ent.getNameEntity().concat("Api");
		String fileCreateDto = "Create".concat(ent.getNameEntity()).concat("Dto");
		String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
		String fileDto = ent.getNameEntity().concat("Dto");
		String interfaceService="I".concat(ent.getNameEntity()).concat("Service");
		String entityMApper=ent.getNameEntity().concat("Mapper");
		String interfaceApi="I".concat(ent.getNameEntity()).concat("Api");
		
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
		myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL
				.concat(ent.getNameEntity())
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		
		myWriter.write(ConstantsImportPackage.IMPORT_ISERVICE
				.concat(interfaceService)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		
		myWriter.write(ConstantsImportPackage.IMPORT_CONFIGURE_MODEL_MAPPER);
		myWriter.write(ConstantsImportPackage.IMPORT_DTO
				.concat(fileCreateDto)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		
		myWriter.write(ConstantsImportPackage.IMPORT_DTO
				.concat(fileViewDto)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		
		myWriter.write(ConstantsImportPackage.IMPORT_DTO
				.concat(fileDto)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
		
		myWriter.write(ConstantsImportPackage.IMPORT_ENTITY_MODEL
				.concat(ent.getNameEntity())
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

		myWriter.write(ConstantsImportPackage.IMPORT_MAPPER
				.concat(entityMApper)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				);
		
		myWriter.write(ConstantsImportPackage.IMPORT_IAPI
				.concat(interfaceApi)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));

		myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API);
		myWriter.write(ConstantsImportPackage.IMPORT_SWAGGER_API_PARAM);

		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(ConstantsAnnotations.ANNOTATION_REST_CONTROLLER);
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(ConstantsAnnotations.ANNOTATION_CROSS_ORIGIN);
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(ConstantsAnnotations.ANNOTATION_API
				.concat(Constants.PARENTHESE_VALUE_EGALE)
				.concat(Constants.DOUBLE_COTE)
				.concat(ent.getNameEntity().toLowerCase())
				.concat(" api")
				.concat(Constants.DOUBLE_COTE)
				.concat(Constants.VIRGULE)
				.concat(" tags")
				.concat(Constants.EGALE)
				.concat(Constants.DOUBLE_COTE)
				.concat(ent.getNameEntity().toLowerCase())
				.concat("-api")
				.concat(Constants.DOUBLE_COTE)
				.concat(Constants.PARENTHESE_FERMANTE)
				);
		
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(ConstantsAnnotations.ANNOTATION_REQUEST_MAPPING
				.concat(Constants.PARENTHESE_VALUE_EGALE)
				.concat(Constants.DOUBLE_COTE)
				.concat(Constants.SLASH_API)
				.concat(Constants.DOUBLE_COTE)
				.concat(Constants.PARENTHESE_FERMANTE)
				);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PUBLIC_CLASS.concat(fileApi)
				.concat(" ")
				.concat(Constants.IMPLEMENTS)
				.concat(" ")
				.concat(interfaceApi)
				.concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.ACCOLADE_OUVRANT)
				.concat(Constants.PATTERN_RETOUR_LIGNE));
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
		myWriter.write(Constants.LOGGER
				.concat(Constants.PARENTHESE_OUVRANTE)
				.concat(ent.getNameEntity())
				.concat("Api")
				.concat(Constants.POINT_CLASS)
				.concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				);
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
		String fileMapper=ent.getNameEntity().concat("Mapper");
		String variableMapper=ent.getNameEntity().toLowerCase().concat("Mapper");
		myWriter.write(Constants.PRIVATE
				.concat(fileMapper)
				.concat(" ")
				.concat(variableMapper)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				);
	}



	private void injectInterfaceService(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
		myWriter.write(Constants.PATTERN_TABULATION);
		String fileService="I".concat(ent.getNameEntity()).concat("Service");
		String variableService=ent.getNameEntity().toLowerCase().concat("Service");
		myWriter.write(Constants.PRIVATE
				.concat(fileService)
				.concat(" ")
				.concat(variableService)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				);
	}



	private void writeWebServicesMethods(BuisnessLogicEntity ent, FileWriter myWriter) {
		// TODO Auto-generated method stub
		
	}



	private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE);
		myWriter.close();
		
	}

}
