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
public class MapperFileWriter {
	
	public void generateMapperFiles(BuisnessLogicEntity entity, Project project) {
		project.getEntities().stream().forEach(ent -> {
			try {
				FileWriter myWriter = writeImportsAndStructureOfClassInMappers(ent);
				injectServicesAndCongigMapper(ent, myWriter);
				writeCreateMapper(ent, myWriter);
				writeViewMapper(ent, myWriter);
				closeAccoladeAndFile(myWriter);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	

	private void writeViewMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
		String fileViewDto="View".concat(ent.getNameEntity()).concat("Dto");
		String fileDto=fileViewDto.toLowerCase();
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PUBLIC
				.concat(fileViewDto)
				.concat(" ")
				.concat("map")
				.concat(ent.getNameEntity())
				.concat("To")
				.concat(fileViewDto)
				.concat(Constants.PARENTHESE_OUVRANTE)
				.concat(ent.getNameEntity())
				.concat(" ")
				.concat(ent.getNameEntity().toLowerCase())
				.concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.ACCOLADE_OUVRANT)
				.concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(fileViewDto)
				.concat(" ")
				.concat(fileDto)
				.concat(Constants.EGALE)
				.concat(Constants.METHODE_MAP)
				.concat(Constants.PARENTHESE_OUVRANTE)
				.concat(ent.getNameEntity().toLowerCase())
				.concat(Constants.VIRGULE)
				.concat(fileViewDto)
				.concat(".class")
				.concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(Constants.RETURN)
				.concat(fileDto)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(Constants.ACCOLADE_FERMANTE));
	}

	private void writeCreateMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
		String fileDto=ent.getNameEntity().toLowerCase().concat("Dto");
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(Constants.PUBLIC
				.concat(ent.getNameEntity())
				.concat(" ")
				.concat("mapCreate")
				.concat(ent.getNameEntity())
				.concat(Constants.PARENTHESE_OUVRANTE)
				.concat("Create")
				.concat(ent.getNameEntity())
				.concat("Dto ")
				.concat(fileDto)
				.concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.ACCOLADE_OUVRANT)
				.concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(ent.getNameEntity())
				.concat(" mapped")
				.concat(ent.getNameEntity())
				.concat(Constants.EGALE)
				.concat(Constants.METHODE_MAP)
				.concat(Constants.PARENTHESE_OUVRANTE)
				.concat(fileDto)
				.concat(Constants.VIRGULE)
				.concat(ent.getNameEntity())
				.concat(".class")
				.concat(Constants.PARENTHESE_FERMANTE)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(Constants.RETURN)
				.concat(fileDto)
				.concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE)
				.concat(Constants.PATTERN_TABULATION)
				.concat(Constants.ACCOLADE_FERMANTE));
		
	}

	private void injectServicesAndCongigMapper(BuisnessLogicEntity ent, FileWriter myWriter) throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(ConstantsAnnotations.ANNOTATION_AUTOWIRED);
		myWriter.write(Constants.PATTERN_TABULATION);
		myWriter.write(ConstantsAnnotations.INJECT_MODEL_MAPPER);
		
	}

	private FileWriter writeImportsAndStructureOfClassInMappers(BuisnessLogicEntity ent) throws IOException {
		File file = new File(ConstantsPath.PATH_TO_SPRINGULAR_MAPPER
				.concat(ent.getNameEntity())
				.concat("Mapper.java"));
		String fileMapper = ent.getNameEntity().concat("Mapper");
		String fileCreateDto = "Create".concat(ent.getNameEntity()).concat("Dto");
		String fileViewDto = "View".concat(ent.getNameEntity()).concat("Dto");
		String fileDto = ent.getNameEntity().concat("Dto");

		FileWriter myWriter = new FileWriter(file);
		myWriter.write("");
		myWriter.write(ConstantsImportPackage.PACKAGE_MAPPER.concat(Constants.PATTERN_RETOUR_LIGNE));
		myWriter.write(ConstantsImportPackage.IMPORT_AUTOWIRED);
		myWriter.write(ConstantsImportPackage.IMPORT_COMPONENT);
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


		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(ConstantsAnnotations.ANNOTATION_COMPONENT);
		myWriter.write(Constants.PUBLIC_CLASS.concat(fileMapper).concat(Constants.PATTERN_RETOUR_LIGNE)
				.concat(Constants.ACCOLADE_OUVRANT).concat(Constants.PATTERN_RETOUR_LIGNE));
		return myWriter;

	}

	private void closeAccoladeAndFile(FileWriter myWriter) throws IOException {
		myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE);
		myWriter.close();
		
	}

}
