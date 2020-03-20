package com.sifast.springular.framework.business.logic.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.relationship.CreateRelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.ViewRelationshipDto;

@Component
public class RelationshipMapper {

	@Autowired
	private ConfiguredModelMapper modelMapper;

	public Relationship mapCreateRelationship(CreateRelationshipDto relationshipDto) {
		Relationship mappedRelationship = modelMapper.map(relationshipDto, Relationship.class);
		return mappedRelationship;
	}

	public ViewRelationshipDto mapRelationshipToViewRelationshipDto(Relationship relationship) {
		ViewRelationshipDto viewRelationshipDto = modelMapper.map(relationship, ViewRelationshipDto.class);
		return viewRelationshipDto;
	}

}
