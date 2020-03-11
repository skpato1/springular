package com.sifast.springular.framework.business.logic.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;

@Table(name = "T_attribute")
@Entity
public class Attribute extends TimestampEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "name_attribute")
	private String nameAttribute;
	@Column(name = "type_attribute")
	private AttributesTypeEnum typeAttribute;
	
	@ManyToOne
	private BuisnessLogicEntity buisnessLogicEntity;

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNameAttribute() {
		return nameAttribute;
	}

	public void setNameAttribute(String nameAttribute) {
		this.nameAttribute = nameAttribute;
	}

	public AttributesTypeEnum getTypeAttribute() {
		return typeAttribute;
	}

	public void setTypeAttribute(AttributesTypeEnum typeAttribute) {
		this.typeAttribute = typeAttribute;
	}

	public BuisnessLogicEntity getBuisness() {
		return buisnessLogicEntity;
	}

	public void setBuisness(BuisnessLogicEntity buisness) {
		this.buisnessLogicEntity = buisness;
	}
	
	
	
	

}
