package com.sifast.springular.framework.business.logic.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;

@Entity
@Table(name = "T_relationship")
public class Relationship extends TimestampEntity implements Serializable { 
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "type_relationship")
	private RelationshipTypeEnum typeRelationship;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private BuisnessLogicEntity parentEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private BuisnessLogicEntity childEntity;
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RelationshipTypeEnum getTypeRelationship() {
		return typeRelationship;
	}

	public void setTypeRelationship(RelationshipTypeEnum typeRelationship) {
		this.typeRelationship = typeRelationship;
	}

	public BuisnessLogicEntity getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(BuisnessLogicEntity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public BuisnessLogicEntity getChildEntity() {
		return childEntity;
	}

	public void setChildEntity(BuisnessLogicEntity childEntity) {
		this.childEntity = childEntity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Relationship [id=");
		builder.append(id);
		builder.append(", typeRelationship=");
		builder.append(typeRelationship);
		builder.append(", parentEntity=");
		builder.append(parentEntity);
		builder.append(", childEntity=");
		builder.append(childEntity);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
