package com.sifast.springular.framework.business.logic.web.dto.buisnessLogicEntity;

import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;

public class BuisnessLogicEntityDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String nameEntity;

    private List<AttributeDto> attributes;

    private Boolean createListIdsIfChild;

    private Boolean createListDtosIfChild;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private int project_id;

    private Boolean isTrackable;

    public Boolean getIsTrackable() {
        return isTrackable;
    }

    public void setIsTrackable(Boolean isTrackable) {
        this.isTrackable = isTrackable;
    }

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

    public String getNameEntity() {
        return nameEntity;
    }

    public void setNameEntity(String nameEntity) {
        this.nameEntity = nameEntity;
    }

    public List<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDto> attributes) {
        this.attributes = attributes;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BuisnessLogicEntityDto{");
        sb.append("nameEntity='").append(nameEntity).append('\'');
        sb.append(", attributes=").append(attributes);
        sb.append(", createListIdsIfChild=").append(createListIdsIfChild);
        sb.append(", createListDtosIfChild=").append(createListDtosIfChild);
        sb.append(", project_id=").append(project_id);
        sb.append(", isTrackable=").append(isTrackable);
        sb.append('}');
        return sb.toString();
    }
}
