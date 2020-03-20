package com.sifast.springular.framework.business.logic.entities;

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

@Entity
@Table(name = "T_project")
public class Project extends TimestampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_project")
	private String nameProject;

	@Column(name = "port_project")
	private String portProject;

	@Column(name = "type_project")
	private String typeProject;

	@Column(name = "path_project")
	private String pathProject;
	
	
	@Column(name = "package_project")
	private String packageProject;
	

	public Project() {

	}

	public Project(String nameProject, String portProject, String typeProject, String pathProject,
			String packageProject) {
		this.nameProject = nameProject;
		this.portProject = portProject;
		this.typeProject = typeProject;
		this.pathProject = pathProject;
		this.packageProject = packageProject;
	}



	public String getPathProject() {
		return pathProject;
	}

	public void setPathProject(String pathProject) {
		this.pathProject = pathProject;
	}

	public String getPortProject() {
		return portProject;
	}

	public void setPortProject(String portProject) {
		this.portProject = portProject;
	}

	public String getTypeProject() {
		return typeProject;
	}

	public void setTypeProject(String typeProject) {
		this.typeProject = typeProject;
	}

	@OneToOne
	private Database database;

	public Database getDatabase() {
		return database;
	}

	public void setDatabase(Database database) {
		this.database = database;
	}

	@OneToMany(cascade = CascadeType.ALL,mappedBy="project",fetch = FetchType.LAZY)
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

	public String getPackageProject() {
		return packageProject;
	}

	public void setPackageProject(String packageProject) {
		this.packageProject = packageProject;
	}

	public void setNameProject(String nameProject) {
		this.nameProject = nameProject;
	}

	

}
