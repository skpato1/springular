package com.sifast.springular.framework.business.logic.web.dto.attribute;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.Constants;

public class AttributeDto {

	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private String nameAttribute;
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
	private AttributesTypeEnum typeAttribute;
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private int entity_id;

	//private BuisnessLogicEntityDto entity_id;

	public int getEntity_id() {
		return entity_id;
	}

	public void setEntity_id(int entity_id) {
		this.entity_id = entity_id;
	}

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

	@Override
	public String toString() {
		return "AttributeDto [nameAttribute=" + nameAttribute + ", typeAttribute=" + typeAttribute + ", entity_id="
				+ entity_id + "]";
	}


	

}
