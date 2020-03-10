package com.sifast.convertToJava.tn.web.dto.attribute;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.convertToJava.tn.common.ApiMessage;
import com.sifast.convertToJava.tn.common.AttributesTypeEnum;
import com.sifast.convertToJava.tn.common.Constants;
import com.sifast.convertToJava.tn.entities.BuisnessLogicEntity;

public class AttributeDto {

	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameAttribute;
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private AttributesTypeEnum typeAttribute;

	private BuisnessLogicEntity buisnessLogicEntity;

	public String getNameAttribute() {
		return nameAttribute;
	}

	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}

	public AttributesTypeEnum getTypeAttribute() {
		return typeAttribute;
	}

	public void setTypeAttribute(AttributesTypeEnum typeAttribute) {
		this.typeAttribute = typeAttribute;
	}

	public BuisnessLogicEntity getBuisnessLogicEntity() {
		return buisnessLogicEntity;
	}

	public void setBuisnessLogicEntity(BuisnessLogicEntity buisnessLogicEntity) {
		this.buisnessLogicEntity = buisnessLogicEntity;
	}

	@Override
	public String toString() {
		return "AttributeDto [nameAttribute=" + nameAttribute + ", typeAttribute=" + typeAttribute + "]";
	}

}
