package com.sifast.springular.framework.business.logic.web.dto.relationship;

import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntity;

public class ViewRelationshipDto extends RelationshipDto {

    private int id;

    private ViewBuisnessLogicEntity parentEntity;

    private ViewBuisnessLogicEntity childEntity;

    public ViewBuisnessLogicEntity getParentEntity() {
        return parentEntity;
    }

    public void setParentEntity(ViewBuisnessLogicEntity parentEntity) {
        this.parentEntity = parentEntity;
    }

    public ViewBuisnessLogicEntity getChildEntity() {
        return childEntity;
    }

    public void setChildEntity(ViewBuisnessLogicEntity childEntity) {
        this.childEntity = childEntity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewRelationshipDto{");
        sb.append("id=").append(id);
        sb.append(", parentEntity=").append(parentEntity);
        sb.append(", childEntity=").append(childEntity);
        sb.append('}');
        return sb.toString();
    }
}
