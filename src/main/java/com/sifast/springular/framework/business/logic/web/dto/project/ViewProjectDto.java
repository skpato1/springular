package com.sifast.springular.framework.business.logic.web.dto.project;

import java.util.List;

import com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;

public class ViewProjectDto extends ProjectDto {
	
	private int id;
	private List<ViewBuisnessLogicEntityDto> entities;
	private ViewDatabaseDto database;

	public List<ViewBuisnessLogicEntityDto> getEntities() {
		return entities;
	}

	public void setEntities(List<ViewBuisnessLogicEntityDto> entities) {
		this.entities = entities;
	}

	public ViewDatabaseDto getDatabase() {
		return database;
	}

	public void setDatabase(ViewDatabaseDto database) {
		this.database = database;
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
		builder.append("ViewProjectDto [id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
