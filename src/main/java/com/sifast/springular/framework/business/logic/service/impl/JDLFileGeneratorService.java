package com.sifast.springular.framework.business.logic.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.DaoFileWriter;
import com.sifast.springular.framework.business.logic.Executor.JdlFileWriter;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;

@Service
public class JDLFileGeneratorService implements IJDLFileGeneratorService {

	@Value("${file.generate.path}")
	private String fileJdlToGenerate;

	@Autowired
	JdlFileWriter jdlFileWriter;
	
	
	@Autowired
	DaoFileWriter daoFileWriter;

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
				File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES
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
				File file = new File(Constants.PATH_TO_SPRINGULAR_FRAMEWORK_SOCLE_MODEL_PACKAGE_FILES
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
	public void writeFilesDao(Project project) throws IOException {
		
		daoFileWriter.writeDaoFiles(project);
	}

	}
