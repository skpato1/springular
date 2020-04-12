package com.sifast.springular.framework.business.logic.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.DtoFileWriter;

import com.sifast.springular.framework.business.logic.Executor.IServiceFileWriter;
import com.sifast.springular.framework.business.logic.Executor.IWebServiceApi;
import com.sifast.springular.framework.business.logic.Executor.DaoFileWriter;
import com.sifast.springular.framework.business.logic.Executor.JdlFileWriter;
import com.sifast.springular.framework.business.logic.Executor.MapperFileWriter;
import com.sifast.springular.framework.business.logic.Executor.WebServiceApiImpl;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.common.constants.ConstantsPath;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;

@Service
public class JDLFileGeneratorService implements IJDLFileGeneratorService {

	@Value("${file.generate.path}")
	private String fileJdlToGenerate;

	@Autowired
	JdlFileWriter jdlFileWriter;

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

	@Override
	public void generateProjectWithJdl(Project project) throws IOException {
		FileWriter myWriter = new FileWriter(fileJdlToGenerate);
		jdlFileWriter.writeProjectDetailsInJdlFile(project, myWriter);
		jdlFileWriter.writeEntitiesInJdlFile(project, myWriter);
		jdlFileWriter.writeRelationshipsInJdlFile(project, myWriter);
		myWriter.close();
	}

	@Override
	public void extendTimeStampInGeneratedEntities(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			try {
				File file = new File(ConstantsPath.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES
						+ entity.getNameEntity().concat(".java"));
				String fileContext = FileUtils.readFileToString(file);
				fileContext = fileContext.replaceAll(Constants.IMPLEMENTS, Constants.EXTEND_TIMESTAMP_ENTITY);
				FileUtils.write(file, fileContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void deleteUnusedCommentsInGeneratedEntities(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			try {
				File file = new File(ConstantsPath.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES
						+ entity.getNameEntity().concat(".java"));
				String fileContext = FileUtils.readFileToString(file);
				fileContext = fileContext.replaceAll(Constants.UNUSED_COMMENTS_FOR_ATTRIBUE, "");
				fileContext = fileContext.replaceAll(Constants.UNUSED_COMMENTS_FOR_METHOD, "");
				FileUtils.write(file, fileContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void createFilesInEachFolderDTO(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			dtoFileWriter.generateViewFilesInEachFolderDTO(entity, project);

		});
		dtoFileWriter.generateCreateFilesInEachFolderDTO(project);
	}

	public void writeFilesIService(Project project) throws IOException {
		serviceFileWriter.writeIServiceFiles(project);
	}

	@Override
	public void writeFilesService(Project project) throws IOException {
		serviceFileWriter.writeImplementServiceFiles(project);
	}

	public void writeFilesDao(Project project) throws IOException {
		daoFileWriter.writeDaoFiles(project);
	}

	@Override
	public void writeFilesMappers(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			mapperFileWriter.generateMapperFiles(entity, project);
		});
			
		
		
	}

	@Override
	public void writeFilesInterfacesWebServicesApi(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			iWebServiceApi.generateIWebServiceApiFiles(entity, project);
		});
		
	}

	@Override
	public void writeFilesWebServicesApiImpl(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			webServiceApiImpl.generateWebServicesImplFiles(entity, project);
		});
		
	}

	@Override
	public void addConstantsInApiMessageFile(Project project) throws IOException {
		project.getEntities().stream().forEach(entity -> {
			try {
				File file = new File(ConstantsPath.PATH_TO_SPRINGULAR_COMMON
						.concat("ApiMessage.java"));
				String fileContext = FileUtils.readFileToString(file);
				if (!fileContext.contains(entity.getNameEntity().toUpperCase().concat(Constants._NOT_FOUND)))
				{
					fileContext = fileContext.replaceFirst("\\{", "\\{"
							.concat(Constants.PATTERN_RETOUR_LIGNE)
							.concat(Constants.PATTERN_RETOUR_LIGNE)
							.concat(Constants.PATTERN_TABULATION)
							.concat(Constants.CONSTANTS_DECLARATION)
							.concat(entity.getNameEntity().toUpperCase())
							.concat(Constants._NOT_FOUND)
							.concat(Constants.EGALE)
							.concat(Constants.DOUBLE_COTE)
							.concat(entity.getNameEntity().toUpperCase())
							.concat(Constants._NOT_FOUND)
							.concat(Constants.DOUBLE_COTE)
							.concat(Constants.PATTERN_POINT_VIRGULE)
							);
					if (!fileContext.contains(entity.getNameEntity().toUpperCase().concat(Constants._CREATED_SUCCESSFULLY)))
					{
						fileContext = fileContext.replaceFirst("\\{", "\\{"
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_RETOUR_LIGNE)
								.concat(Constants.PATTERN_TABULATION)
								.concat(Constants.CONSTANTS_DECLARATION)
								.concat(entity.getNameEntity().toUpperCase())
								.concat(Constants._CREATED_SUCCESSFULLY)
								.concat(Constants.EGALE)
								.concat(Constants.DOUBLE_COTE)
								.concat(entity.getNameEntity().toUpperCase())
								.concat(Constants._CREATED_SUCCESSFULLY)
								.concat(Constants.DOUBLE_COTE)
								.concat(Constants.PATTERN_POINT_VIRGULE)
								);
					}
				}
					
				FileUtils.write(file, fileContext);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
	}

}
