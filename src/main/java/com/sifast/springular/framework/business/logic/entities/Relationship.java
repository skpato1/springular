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
	private BuisnessLogicEntity masterEntity;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private BuisnessLogicEntity slaveEntity;
	
	
	
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

	public BuisnessLogicEntity getMasterEntity() {
		return masterEntity;
	}

	public void setMasterEntity(BuisnessLogicEntity masterEntity) {
		this.masterEntity = masterEntity;
	}

	public BuisnessLogicEntity getSlaveEntity() {
		return slaveEntity;
	}

	public void setSlaveEntity(BuisnessLogicEntity slaveEntity) {
		this.slaveEntity = slaveEntity;
	}
	
	
	@Override
	public String toString() {
		return "Relationship [id=" + id + ", typeRelationship=" + typeRelationship + ", masterEntity=" + masterEntity
				+ ", slaveEntity=" + slaveEntity + "]";
	}

}
