package com.sifast.springular.framework.business.logic.web.mapper;

import com.sifast.springular.framework.business.logic.entities.Database;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.DatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseMapper {

    @Autowired
    private ConfiguredModelMapper modelMapper;

    public Database mapCreateDatabase(CreateDatabaseDto databaseDto) {
        return modelMapper.map(databaseDto, Database.class);
    }

    public Database mapDatabaseDtoToModelDatabase(DatabaseDto databaseDto) {
        return modelMapper.map(databaseDto, Database.class);
    }

    public ViewDatabaseDto mapDatabaseToViewDatabaseDto(Database database) {
        return modelMapper.map(database, ViewDatabaseDto.class);
    }

}
