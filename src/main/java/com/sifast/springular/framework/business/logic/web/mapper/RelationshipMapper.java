package com.sifast.springular.framework.business.logic.web.mapper;

import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.relationship.CreateRelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.RelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.ViewRelationshipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelationshipMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public Relationship mapCreateRelationship(CreateRelationshipDto relationshipDto) {
        return modelMapper.map(relationshipDto, Relationship.class);
    }

    public Relationship mapRelationship(RelationshipDto relationshipDto) {
        return modelMapper.map(relationshipDto, Relationship.class);
    }

    public ViewRelationshipDto mapRelationshipToViewRelationshipDto(Relationship relationship) {
        return modelMapper.map(relationship, ViewRelationshipDto.class);
    }

}
