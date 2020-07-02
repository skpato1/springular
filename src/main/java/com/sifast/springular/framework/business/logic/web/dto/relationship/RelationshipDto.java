package com.sifast.springular.framework.business.logic.web.dto.relationship;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;

public class RelationshipDto {
	
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private RelationshipTypeEnum typeRelationship;
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private int parentEntity_id;
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private int childEntity_id;


	public RelationshipTypeEnum getTypeRelationship() {
		return typeRelationship;
	}


	public void setTypeRelationship(RelationshipTypeEnum typeRelationship) {
		this.typeRelationship = typeRelationship;
	}


	public int getParentEntity_id() {
		return parentEntity_id;
	}


	public void setParentEntity_id(int parentEntity_id) {
		this.parentEntity_id = parentEntity_id;
	}


	public int getChildEntity_id() {
		return childEntity_id;
	}


	public void setChildEntity_id(int childEntity_id) {
		this.childEntity_id = childEntity_id;
	}


	@Override
	public String toString() {
		return "RelationshipDto [typeRelationship=" + typeRelationship + ", parentEntity_id=" + parentEntity_id
				+ ", childEntity_id=" + childEntity_id + "]";
	}
	
	
	

}
