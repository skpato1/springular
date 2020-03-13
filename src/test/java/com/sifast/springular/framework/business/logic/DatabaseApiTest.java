package com.sifast.springular.framework.business.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.CustomMatcher;
import com.sifast.springular.framework.business.logic.entities.Database;
import com.sifast.springular.framework.business.logic.web.dto.database.CreateDatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.DatabaseDto;
import com.sifast.springular.framework.business.logic.web.dto.database.ViewDatabaseDto;

public class DatabaseApiTest extends SpringularFrameworkBusinessLogicApplicationTests{
	
	
	private CreateDatabaseDto createDatabaseDto;
	
	@Before
	public void setUp() {
		createDatabaseDto = getDatabaseDto();
	}
	
	
	@Test
	public void shouldThrowExceptionWhenTryToGetInexistantDatabase() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.DATABASE_NOT_FOUND, HttpStatus.NOT_FOUND));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> template = restTemplate.getForEntity("http://localhost:8080/api/database/"+343, Database.class);
				
	}
	@Test
	public void shouldThrowExceptionWhenTryToSaveDatabase() throws IOException{
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<CreateDatabaseDto> request = new HttpEntity<>(createDatabaseDto);
	    ViewDatabaseDto DatabaseFromWebService = restTemplate.postForObject("http://localhost:8080/api/database", request, ViewDatabaseDto.class);
	    assertNotNull(DatabaseFromWebService);
	    assertEquals(DatabaseFromWebService.getNameDatabase(), createDatabaseDto.getNameDatabase());
	}
	
	@Test
	public void shouldThrowExceptionWhenTryToUpdateDatabase() throws IOException{
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",1);
	    DatabaseDto updatedDatabaseDto = getDatabaseDto();
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<DatabaseDto> request = new HttpEntity<>(updatedDatabaseDto);
	    restTemplate.put("http://localhost:8080/api/database/{id}", request,params);
	}
	@Test
	public void shouldThrowExceptionWhenTryToDeleteDatabase() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.DATABASE_NOT_FOUND, HttpStatus.NOT_FOUND));
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",89);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete("http://localhost:8080/api/database/{id}",params);
	}
	
	private CreateDatabaseDto getDatabaseDto() {
		CreateDatabaseDto createDatabaseDto = new CreateDatabaseDto();
		createDatabaseDto.setNameDatabase("spring-db-test-kmar");
		createDatabaseDto.setTypeDatabase("Mysql");
		return createDatabaseDto;
	}

}
