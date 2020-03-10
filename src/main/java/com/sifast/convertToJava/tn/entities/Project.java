package com.sifast.convertToJava.tn.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name = "T_project")
@Entity
public class Project extends TimestampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_project")
	private String nameProject;
	
	@OneToOne
	private Database database;
	
	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	@OneToMany(cascade=CascadeType.ALL , mappedBy="project" ,fetch=FetchType.LAZY)
	private List<BuisnessLogicEntity> entities;
	

	public List<BuisnessLogicEntity> getEntities() {
		return entities;
	}

	public void setEntities(List<BuisnessLogicEntity> entities) {
		this.entities = entities;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameProject() {
		return nameProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	

	
}
