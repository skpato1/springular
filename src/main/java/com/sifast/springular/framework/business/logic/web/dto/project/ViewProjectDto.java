package com.sifast.springular.framework.business.logic.web.dto.project;

public class ViewProjectDto extends ProjectDto {
	
	private int id;

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
