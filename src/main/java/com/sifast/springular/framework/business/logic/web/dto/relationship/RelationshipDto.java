package com.sifast.springular.framework.business.logic.web.dto.relationship;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.RelationshipTypeEnum;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RelationshipDto {


    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private RelationshipTypeEnum typeRelationship;


    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private int parentEntityId;


    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private int childEntityId;


    public RelationshipTypeEnum getTypeRelationship() {
        return typeRelationship;
    }


    public void setTypeRelationship(RelationshipTypeEnum typeRelationship) {
        this.typeRelationship = typeRelationship;
    }


    public int getParentEntityId() {
        return parentEntityId;
    }


    public void setParentEntityId(int parentEntityId) {
        this.parentEntityId = parentEntityId;
    }


    public int getChildEntityId() {
        return childEntityId;
    }


    public void setChildEntityId(int childEntityId) {
        this.childEntityId = childEntityId;
    }


    @Override
    public String toString() {
        return "RelationshipDto [typeRelationship=" + typeRelationship + ", parentEntity_id=" + parentEntityId
                + ", childEntity_id=" + childEntityId + "]";
    }


}
