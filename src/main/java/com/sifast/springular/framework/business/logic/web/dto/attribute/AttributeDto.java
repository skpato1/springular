package com.sifast.springular.framework.business.logic.web.dto.attribute;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.constants.Constants;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AttributeDto {

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private String nameAttribute;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @Size(max = Constants.MAX_STRING_SIZE, message = ApiMessage.STRING_SIZE_VALIDATION_FAILED)
    private AttributesTypeEnum typeAttribute;

    @NotEmpty(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    @NotNull(message = ApiMessage.REQUIRED_VALIDATION_FAILED)
    private int entityId;

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
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

    @Override
    public String toString() {
        return "AttributeDto [nameAttribute=" + nameAttribute + ", typeAttribute=" + typeAttribute + ", entity_id="
                + entityId + "]";
    }


}
