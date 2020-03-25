package com.sifast.springular.framework.business.logic.Executor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.Project;

@Component
public class DtoFileWriter {
	
	
	
	public void generateSuperFilesInEachFolderDTO(FileWriter myWriter) {
		
	}

	public void generateCreateFilesInEachFolderDTO(FileWriter myWriter) {
		
		
	}

	public void generateViewFilesInEachFolderDTO(FileWriter myWriter) {
		
	}

	public void createFilesInEachFolderDTO(Project project) {
		
		project.getEntities().stream().forEach(entity->{
			File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
			.concat(entity.getNameEntity().toLowerCase())
			.concat(Constants.PATTERN_SLASH)
			.concat(entity.getNameEntity()
			.concat("Dto.java")));
			
			
			File fileView = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
					.concat(entity.getNameEntity().toLowerCase())
					.concat(Constants.PATTERN_SLASH).concat("View")
					.concat(entity.getNameEntity()
					.concat("Dto.java")));
			
			File fileCreate = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_DTO_FOLDERS_PACKAGE_FILES
					.concat(entity.getNameEntity().toLowerCase())
					.concat(Constants.PATTERN_SLASH).concat("Create")
					.concat(entity.getNameEntity()
					.concat("Dto.java")));
			
			try {
				FileWriter myWriter=new FileWriter(file);
				generateSuperFilesInEachFolderDTO(myWriter);
				FileWriter myWriterView=new FileWriter(fileView);
				generateViewFilesInEachFolderDTO(myWriterView);
				FileWriter myWriterCreate=new FileWriter(fileCreate);
				generateCreateFilesInEachFolderDTO(myWriterCreate);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		});
		
	}
	
public void createMapperFiles(Project project) {
		
		project.getEntities().stream().forEach(entity->{
			File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MAPPER
			.concat(entity.getNameEntity())
			.concat("Mapper.java"));
			
			
			
			
			try {
				FileWriter myWriter=new FileWriter(file);
				

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		});
		
	}

	
}
