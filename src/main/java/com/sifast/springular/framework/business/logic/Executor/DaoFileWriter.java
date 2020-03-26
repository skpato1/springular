package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class DaoFileWriter {
	
	
	public void writeDaoFiles(Project project) throws IOException {
		project.getEntities().stream().forEach(entity->{
			try {
				File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DAO_PACKAGE_FILES
				.concat("I")+ entity.getNameEntity().concat("Dao.java"));
				String fileDao="I".concat(entity.getNameEntity()).concat("Dao");
				FileWriter myWriter= new FileWriter(file);
				myWriter.write("");
				myWriter.write(Constants.PACKAGE_SPRINGULAR_DAO.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_SPRINGULAR_DOMAIN_TO_REPLACE.concat(entity.getNameEntity()).concat(Constants.PATTERN_POINT_VIRGULE__ET_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_SPRINGULAR_REPOSITORY_ALL.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.IMPORT_SPRINGULAR_REPOSITORY.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ANNOTATION_REPOSITORY.concat(Constants.PATTERN_RETOUR_LIGNE));
				myWriter.write(Constants.PUBLIC_INTERFACE
						.concat(fileDao)
						.concat(Constants.EXTENDS)
						.concat(Constants.IGENERICDAO)
						.concat(entity.getNameEntity())
						.concat(Constants.VIRGULE)
						.concat(Constants.LONG));
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ACCOLADE_OUVRANT);
				myWriter.write(Constants.PATTERN_RETOUR_LIGNE);
				myWriter.write(Constants.ACCOLADE_FERMANTE);
				myWriter.close();

			}

			catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		
	}

}
