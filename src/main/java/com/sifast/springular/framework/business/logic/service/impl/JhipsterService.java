package com.sifast.springular.framework.business.logic.service.impl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IJhipsterService;

@Service
public class JhipsterService implements IJhipsterService {

	@Value("${file.generate.path}")
    private String fileJdlToGenerate;
	
	
	@Override
	public void generateProjectWithJdl(Project project) throws IOException {
		FileWriter myWriter = new FileWriter(fileJdlToGenerate);
		myWriter.write("application "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"config "+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"baseName "+project.getNameProject()+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"packageName "+project.getPackageProject()+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"applicationType "+project.getTypeProject()+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"serverPort "+project.getPortProject()+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.PATTERN_TABULATION+"enableHibernateCache false"+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write("entities *"+Constants.PATTERN_RETOUR_LIGNE);
		myWriter.write(Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);
		
		  List<BuisnessLogicEntity> entities=project.getEntities(); 
		  for (int i = 0; i < entities.size(); i++)
		  {
			  myWriter.write("entity "+entities.get(i).getNameEntity()+Constants.ACCOLADE_OUVRANT+Constants.PATTERN_RETOUR_LIGNE);
			  List<Attribute>attributes=entities.get(i).getAttributes();
			  for (int j = 0; j <attributes.size(); j++) 
			  	{ 
				  	  myWriter.write(Constants.PATTERN_TABULATION+attributes.get(j).
				  	  getNameAttribute()+" "+attributes.get(j).getTypeAttribute()+Constants.PATTERN_RETOUR_LIGNE);
				 
				}
				myWriter.write(Constants.ACCOLADE_FERMANTE+Constants.PATTERN_RETOUR_LIGNE);

		  } 
		  
		myWriter.close();
	}

	
}
