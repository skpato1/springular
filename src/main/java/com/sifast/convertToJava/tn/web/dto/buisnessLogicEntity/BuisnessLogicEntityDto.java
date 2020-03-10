package com.sifast.convertToJava.tn.web.dto.buisnessLogicEntity;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.convertToJava.tn.common.ApiMessage;
import com.sifast.convertToJava.tn.common.Constants;
import com.sifast.convertToJava.tn.entities.Attribute;
import com.sifast.convertToJava.tn.entities.Project;

public class BuisnessLogicEntityDto {
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameEntity;
	
	private List<Attribute> attributes;
	private Project project;
	
	public String getNameEntity() {
		return nameEntity;
	}
	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "BuisnessLogicEntityDto [nameEntity=" + nameEntity + "]";
	}


}
