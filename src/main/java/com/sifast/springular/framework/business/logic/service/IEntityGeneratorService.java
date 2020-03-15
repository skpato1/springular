package com.sifast.springular.framework.business.logic.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IEntityGeneratorService {
	public String functionEquals(String classname);
	public String functionHash();
	public String functionToString(String classname,List<String> fieldName);
	public void generateEntityDynamicWithArrayAngular(String className, List<String> fieldName, List<String> fieldType);
	

}
