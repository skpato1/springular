package com.sifast.springular.framework.business.logic.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "T_database")
@Entity
public class Database extends TimestampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_database")
	private String nameDatabase;
	
	@Column(name = "type_database")
	private String typeDatabase;
	
	
	public String getTypeDatabase() {
		return typeDatabase;
	}

	public void setTypeDatabase(String typeDatabase) {
		this.typeDatabase = typeDatabase;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameDatabase() {
		return nameDatabase;
	}

	public void setNameDatabase(String nameDatabase) {
		this.nameDatabase = nameDatabase;
	}

	

	

}
