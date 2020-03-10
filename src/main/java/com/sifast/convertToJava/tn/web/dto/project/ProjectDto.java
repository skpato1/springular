package com.sifast.convertToJava.tn.web.dto.project;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.convertToJava.tn.common.ApiMessage;
import com.sifast.convertToJava.tn.common.Constants;
import com.sifast.convertToJava.tn.entities.BuisnessLogicEntity;
import com.sifast.convertToJava.tn.entities.Database;

public class ProjectDto {
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameProject;
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String portProject;
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String typeProject;
	
	
	private Database database;
	private List<BuisnessLogicEntity> entities;



	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	public List<BuisnessLogicEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<BuisnessLogicEntity> entities) {
		this.entities = entities;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	public String getPortProject() {
		return portProject;
	}

	public void setPortProject(String portProject) {
		this.portProject = portProject;
	}

	public String getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(String typeProject) {
		this.typeProject = typeProject;
	}

	@Override
	public String toString() {
		return "ProjectDto [nameProject=" + nameProject + ", portProject=" + portProject + ", typeProject="
				+ typeProject + "]";
	}

}
