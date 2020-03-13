package com.sifast.springular.framework.business.logic.web.dto.database;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.Constants;

public class DatabaseDto {
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameDatabase;
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String typeDatabase;
	
	
	public String getNameDatabase() {
		return nameDatabase;
	}
	public void setNameDatabase(String nameDatabase) {
		this.nameDatabase = nameDatabase;
	}
	public String getTypeDatabase() {
		return typeDatabase;
	}
	public void setTypeDatabase(String typeDatabase) {
		this.typeDatabase = typeDatabase;
	}
	@Override
	public String toString() {
		return "DatabaseDto [nameDatabase=" + nameDatabase + ", typeDatabase=" + typeDatabase + "]";
	}
	
	
	
	
	
	
	

}
