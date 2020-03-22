package com.sifast.springular.framework.business.logic.service.impl;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.Executor.JdlFileWriter;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IJDLFileGeneratorService;

@Service
public class JDLFileGeneratorService implements IJDLFileGeneratorService {

	@Value("${file.generate.path}")
	private String fileJdlToGenerate;
	
	@Autowired
	JdlFileWriter jdlFileWriter;

	@Override
	public void generateProjectWithJdl(Project project) throws IOException {
		FileWriter myWriter = new FileWriter(fileJdlToGenerate);
		jdlFileWriter.writeProjectDetailsInJdlFile(project, myWriter);
		jdlFileWriter.writeEntitiesInJdlFile(project, myWriter);
		jdlFileWriter.writeRelationshipsInJdlFile(project, myWriter);
		myWriter.close();
	}

	

}
