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
	private int masterEntity_id;
	
	
	@NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	@NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
	private int slaveEntity_id;


	public RelationshipTypeEnum getTypeRelationship() {
		return typeRelationship;
	}


	public void setTypeRelationship(RelationshipTypeEnum typeRelationship) {
		this.typeRelationship = typeRelationship;
	}


	public int getMasterEntity_id() {
		return masterEntity_id;
	}


	public void setMasterEntity_id(int masterEntity_id) {
		this.masterEntity_id = masterEntity_id;
	}


	public int getSlaveEntity_id() {
		return slaveEntity_id;
	}


	public void setSlaveEntity_id(int slaveEntity_id) {
		this.slaveEntity_id = slaveEntity_id;
	}


	@Override
	public String toString() {
		return "RelationshipDto [typeRelationship=" + typeRelationship + ", masterEntity_id=" + masterEntity_id
				+ ", slaveEntity_id=" + slaveEntity_id + "]";
	}
	
	
	

}
