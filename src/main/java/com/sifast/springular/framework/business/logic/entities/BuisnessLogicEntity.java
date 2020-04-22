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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "T_entity")
public class BuisnessLogicEntity extends TimestampEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name_entity")
	private String nameEntity;
	
	@Column(name = "create_list_ids_if_slave")
	private Boolean createListIdsIfSlave;
	
	@Column(name = "create_list_dtos_if_slave")
	private Boolean createListDtosIfSlave;
	
	@OneToMany(cascade=CascadeType.ALL , mappedBy="buisnessLogicEntity" ,fetch=FetchType.LAZY)
	private List<Attribute> attributes;
	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Project project;
	
	@OneToMany(cascade=CascadeType.ALL , mappedBy="masterEntity" ,fetch=FetchType.LAZY)
	private List<Relationship> relationshipsMaster;
	
	@OneToMany(cascade=CascadeType.ALL , mappedBy="slaveEntity" ,fetch=FetchType.LAZY)
	private List<Relationship> relationshipsSlave;
	
	

	public Boolean getCreateListIdsIfSlave() {
		return createListIdsIfSlave;
	}

	public void setCreateListIdsIfSlave(Boolean createListIdsIfSlave) {
		this.createListIdsIfSlave = createListIdsIfSlave;
	}

	public Boolean getCreateListDtosIfSlave() {
		return createListDtosIfSlave;
	}

	

	public void setCreateListDtosIfSlave(Boolean createListDtosIfSlave) {
		this.createListDtosIfSlave = createListDtosIfSlave;
	}

	
	
	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Relationship> getRelationshipsMaster() {
		return relationshipsMaster;
	}

	public void setRelationshipsMaster(List<Relationship> relationshipsMaster) {
		this.relationshipsMaster = relationshipsMaster;
	}

	public List<Relationship> getRelationshipsSlave() {
		return relationshipsSlave;
	}

	public void setRelationshipsSlave(List<Relationship> relationshipsSlave) {
		this.relationshipsSlave = relationshipsSlave;
	}

	public String getNameEntity() {
		return nameEntity;
	}

	public void setNameEntity(String nameEntity) {
		this.nameEntity = nameEntity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BuisnessLogicEntity [id=");
		builder.append(id);
		builder.append(", nameEntity=");
		builder.append(nameEntity);
		builder.append(", createListIdsIfSlave=");
		builder.append(createListIdsIfSlave);
		builder.append(", createListDtosIfSlave=");
		builder.append(createListDtosIfSlave);
		builder.append(", attributes=");
		builder.append(attributes);
		builder.append(", project=");
		builder.append(project);
		builder.append(", relationshipsMaster=");
		builder.append(relationshipsMaster);
		builder.append(", relationshipsSlave=");
		builder.append(relationshipsSlave);
		builder.append("]");
		return builder.toString();
	}

}
