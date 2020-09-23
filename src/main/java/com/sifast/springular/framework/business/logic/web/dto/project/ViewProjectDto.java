package com.sifast.springular.framework.business.logic.web.dto.project;

import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;
import java.util.List;

public class ViewProjectDto extends ProjectDto {

    private int id;
    private List<ViewBuisnessLogicEntityDto> entities;
    private ViewDatabaseDto database;

    public List<ViewBuisnessLogicEntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<ViewBuisnessLogicEntityDto> entities) {
        this.entities = entities;
    }

    public ViewDatabaseDto getDatabase() {
        return database;
    }

    public void setDatabase(ViewDatabaseDto database) {
        this.database = database;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ViewProjectDto [id=");
        builder.append(id);
        builder.append("]");
        return builder.toString();
    }

}
