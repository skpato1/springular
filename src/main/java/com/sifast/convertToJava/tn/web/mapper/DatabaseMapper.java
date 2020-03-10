package com.sifast.convertToJava.tn.web.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.convertToJava.tn.entities.Database;
import com.sifast.convertToJava.tn.web.config.ConfiguredModelMapper;
import com.sifast.convertToJava.tn.web.dto.database.CreateDatabaseDto;
import com.sifast.convertToJava.tn.web.dto.database.ViewDatabaseDto;

@Component
public class DatabaseMapper {

	@Autowired
	private ConfiguredModelMapper modelMapper;

	public Database mapCreateDatabase(CreateDatabaseDto databaseDto) {
		Database mappedDatabase = modelMapper.map(databaseDto, Database.class);
		return mappedDatabase;
	}

	public ViewDatabaseDto mapDatabaseToViewDatabaseDto(Database database) {
		ViewDatabaseDto viewDatabaseDto = modelMapper.map(database, ViewDatabaseDto.class);
		return viewDatabaseDto;
	}

}
