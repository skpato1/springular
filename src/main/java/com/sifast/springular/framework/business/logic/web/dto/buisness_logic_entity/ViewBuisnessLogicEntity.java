package com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity;

import com.sifast.springular.framework.business.logic.web.dto.project.ViewProject;

public class ViewBuisnessLogicEntity {

    private int id;

    private ViewProject project;

    private String nameEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ViewProject getProject() {
        return project;
    }

    public void setProject(ViewProject project) {
        this.project = project;
    }

    public String getNameEntity() {
        return nameEntity;
    }

    public void setNameEntity(String nameEntity) {
        this.nameEntity = nameEntity;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ViewBuisnessLogicEntity{");
        sb.append("id=").append(id);
        sb.append(", project=").append(project);
        sb.append(", nameEntity='").append(nameEntity).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
