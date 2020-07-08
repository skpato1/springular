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

    @Column(name = "create_list_ids_if_child")
    private Boolean createListIdsIfChild;

    @Column(name = "create_list_dtos_if_child")
    private Boolean createListDtosIfChild;

    @Column(name = "is_trackable")
    private Boolean isTrackable;

    public Boolean getIsTrackable() {
        return isTrackable;
    }

    public void setIsTrackable(Boolean isTrackable) {
        this.isTrackable = isTrackable;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buisnessLogicEntity", fetch = FetchType.LAZY)
    private List<Attribute> attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parentEntity", fetch = FetchType.LAZY)
    private List<Relationship> relationshipsParent;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "childEntity", fetch = FetchType.LAZY)
    private List<Relationship> relationshipsChild;

    public Boolean getCreateListIdsIfChild() {
        return createListIdsIfChild;
    }

    public void setCreateListIdsIfChild(Boolean createListIdsIfChild) {
        this.createListIdsIfChild = createListIdsIfChild;
    }

    public Boolean getCreateListDtosIfChild() {
        return createListDtosIfChild;
    }

    public void setCreateListDtosIfChild(Boolean createListDtosIfChild) {
        this.createListDtosIfChild = createListDtosIfChild;
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

    public List<Relationship> getRelationshipsParent() {
        return relationshipsParent;
    }

    public void setRelationshipsParent(List<Relationship> relationshipsParent) {
        this.relationshipsParent = relationshipsParent;
    }

    public List<Relationship> getRelationshipsChild() {
        return relationshipsChild;
    }

    public void setRelationshipsChild(List<Relationship> relationshipsChild) {
        this.relationshipsChild = relationshipsChild;
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
        builder.append(", createListIdsIfChild=");
        builder.append(createListIdsIfChild);
        builder.append(", createListDtosIfChild=");
        builder.append(createListDtosIfChild);
        builder.append(", attributes=");
        builder.append(attributes);
        builder.append(", project=");
        builder.append(project);
        builder.append(", relationshipsParent=");
        builder.append(relationshipsParent);
        builder.append(", relationshipsChild=");
        builder.append(relationshipsChild);
        builder.append("]");
        return builder.toString();
    }

}
