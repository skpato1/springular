package com.sifast.springular.framework.business.logic.web.dto.relationship;

import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;

public class ViewRelationshipDto extends RelationshipDto {
	
	
	private int id;
	
	private ViewBuisnessLogicEntityDto masterEntity;
	
	private ViewBuisnessLogicEntityDto slaveEntity;


	public ViewBuisnessLogicEntityDto getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(ViewBuisnessLogicEntityDto masterEntity) {
		this.masterEntity = masterEntity;
	}

	public ViewBuisnessLogicEntityDto getSlaveEntity() {
		return slaveEntity;
	}

	public void setSlaveEntity(ViewBuisnessLogicEntityDto slaveEntity) {
		this.slaveEntity = slaveEntity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ViewRelationshipDto [id=");
		builder.append(id);
		builder.append(", masterEntity=");
		builder.append(masterEntity);
		builder.append(", slaveEntity=");
		builder.append(slaveEntity);
		builder.append("]");
		return builder.toString();
	}
}
