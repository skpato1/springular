package com.sifast.springular.framework.business.logic.web.dto.relationship;

import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;

public class ViewRelationshipDto extends RelationshipDto {
	
	
	private int id;
	
	private ViewBuisnessLogicEntityDto parentEntity;
	
	private ViewBuisnessLogicEntityDto childEntity;


	public ViewBuisnessLogicEntityDto getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(ViewBuisnessLogicEntityDto parentEntity) {
		this.parentEntity = parentEntity;
	}

	public ViewBuisnessLogicEntityDto getChildEntity() {
		return childEntity;
	}

	public void setChildEntity(ViewBuisnessLogicEntityDto childEntity) {
		this.childEntity = childEntity;
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
		builder.append(", parentEntity=");
		builder.append(parentEntity);
		builder.append(", childEntity=");
		builder.append(childEntity);
		builder.append("]");
		return builder.toString();
	}
}
