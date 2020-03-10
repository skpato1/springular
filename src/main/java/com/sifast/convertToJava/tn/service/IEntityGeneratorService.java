package com.sifast.convertToJava.tn.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface IEntityGeneratorService {
	public String functionEquals(String classname);
	public String functionHash();
	public String functionToString(String classname,String[] fieldName);
	public void generateEntityDynamicWithArrayAngular(String className, String[] fieldName, String[] fieldType);

}
