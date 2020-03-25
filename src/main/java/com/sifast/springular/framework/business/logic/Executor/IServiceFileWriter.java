package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class IServiceFileWriter {
	
	public void writeIServiceFiles(Project project) throws IOException {
		project.getEntities().stream().forEach(entity->{
			File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_SERVICE_PACKAGE_FILES
					.concat("I").concat(entity.getNameEntity()).concat("Service.java"));
			try {
				FileWriter myWriter=new FileWriter(file);
				String fileService="I".concat(entity.getNameEntity()).concat("Service");
				myWriter.write(Constants.PACKAGE_SPRINGULAR_SERVICE.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_TRANSACTIONAL.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ANNOTATION_TRANSACTIONAL);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.INTERFACE_PUBLIC
						.concat(fileService)
						.concat(Constants.EXTENDS)
						.concat(Constants.IGENERIC_SERVICE)
						.concat(entity.getNameEntity())
						.concat(Constants.VIRGULE)
						.concat(Constants.LONG)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.ACCOLADE_FERMANTE));
				myWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	public void writeImplementServiceFiles(Project project) throws IOException{
		project.getEntities().stream().forEach(entity->{
			File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_SERVICE_IMPL_PACKAGE_FILES
					.concat(entity.getNameEntity()).concat("Service.java"));
			try
			{
				
				FileWriter myWriter=new FileWriter(file);
				String fileService=entity.getNameEntity().concat("Service");
				myWriter.write(Constants.PACKAGE_SPRINGULAR_SERVICE_IMPL.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_ANNOTATION_SERVICE.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_ENTITY_MODEL.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_INTERFACE_SERVICE.concat("I").concat(fileService).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ANNOTATION_SERVICE.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.PUBLIC_CLASS
						.concat(fileService)
						.concat(Constants.EXTENDS)
						.concat(Constants.GENERIC_SERVICE)
						.concat(entity.getNameEntity())
						.concat(Constants.VIRGULE)
						.concat(Constants.LONG).concat(" ")
						.concat(Constants.IMPLEMENTS).concat(" ")
						.concat("I").concat(fileService)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.ACCOLADE_OUVRANT)
						.concat(Constants.PATTERN_RETOUR_LIGNE)
						.concat(Constants.ACCOLADE_FERMANTE));
				myWriter.close();
			} 
			
			
			
			catch (IOException e) {
				e.printStackTrace();
			}

		});
	}
}
