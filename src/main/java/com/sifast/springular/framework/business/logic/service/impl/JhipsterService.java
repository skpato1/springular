package com.sifast.springular.framework.business.logic.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.service.IJhipsterService;

@Service
public class JhipsterService implements IJhipsterService {

	@Value("${file.generate.path}")
    private String fileJdlToGenerate;
	
	@Override
	public void generateEntityWithJdl(List<String> className, List<String> fieldName, List<String> fieldType) throws IOException {
		

	}

	@Override
	public void generateProjectWithJdl(String baseName,String packageName,String applicationType,String serverPort) throws IOException {
		FileWriter myWriter = new FileWriter(fileJdlToGenerate);
		myWriter.write("application {"+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"config {"+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"baseName "+baseName+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"packageName "+packageName+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"applicationType "+applicationType+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"serverPort "+serverPort+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write("entities *"+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.close();
		
	}

	
}
