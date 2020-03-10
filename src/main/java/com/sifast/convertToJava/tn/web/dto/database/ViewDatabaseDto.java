package com.sifast.convertToJava.tn.web.dto.database;

public class ViewDatabaseDto extends DatabaseDto {

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
