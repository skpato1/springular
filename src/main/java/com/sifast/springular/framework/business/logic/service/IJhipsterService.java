package com.sifast.springular.framework.business.logic.service;

import java.io.IOException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IJhipsterService {

	public void generateEntityWithJdl(List<String> className, List<String> fieldName, List<String> fieldType) throws IOException;
	public void generateProjectWithJdl(String baseName,String packageName,String applicationType,String serverPort) throws IOException;

	
}
