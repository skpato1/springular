package com.sifast.springular.framework.business.logic.web.dto.project;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.Constants;
import com.sifast.springular.framework.business.logic.common.ProjectStatus;

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
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String pathProject;
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String usernameProject;
	
	
	public String getUsernameProject() {
		return usernameProject;
	}

	public void setUsernameProject(String usernameProject) {
		this.usernameProject = usernameProject;
	}

	private ProjectStatus statusProject;


	

	public ProjectStatus getStatusProject() {
		return statusProject;
	}

	public void setStatusProject(ProjectStatus statusProject) {
		this.statusProject = statusProject;
	}

	public String getPathProject() {
		return pathProject;
	}

	public void setPathProject(String pathProject) {
		this.pathProject = pathProject;
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
		StringBuilder builder = new StringBuilder();
		builder.append("ProjectDto [nameProject=");
		builder.append(nameProject);
		builder.append(", portProject=");
		builder.append(portProject);
		builder.append(", typeProject=");
		builder.append(typeProject);
		builder.append(", pathProject=");
		builder.append(pathProject);
		builder.append(", usernameProject=");
		builder.append(usernameProject);
		builder.append(", statusProject=");
		builder.append(statusProject);
		builder.append("]");
		return builder.toString();
	}

	

	
	

}
