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
import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.common.CustomMatcher;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.ViewAttributeDto;

public class AttributeApiTest extends SpringularFrameworkBusinessLogicApplicationTests {
	
	private CreateAttributeDto createAttributeDto;
	
	@Before
	public void setUp() {
		createAttributeDto = getAttributeDto();
	}
	
	@Test
	public void shouldThrowExceptionWhenTryToGetInexistantAttribute() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.ATTRIBUTE_NOT_FOUND, HttpStatus.NOT_FOUND));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> template = restTemplate.getForEntity("http://localhost:8080/api/attribute/"+343, Attribute.class);
				
	}
	@Test
	public void shouldThrowExceptionWhenTryToSaveAttribute() throws IOException{
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<CreateAttributeDto> request = new HttpEntity<>(createAttributeDto);
	    ViewAttributeDto attributeFromWebService = restTemplate.postForObject("http://localhost:8080/api/attribute", request, ViewAttributeDto.class);
	    assertNotNull(attributeFromWebService);
	    assertEquals(attributeFromWebService.getNameAttribute(), createAttributeDto.getNameAttribute());
	}
	
	@Test
	public void shouldThrowExceptionWhenTryToUpdateAttribute() throws IOException{
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",1);
	    AttributeDto updatedAttributeDto = getAttributeDto();
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<AttributeDto> request = new HttpEntity<>(updatedAttributeDto);
	    restTemplate.put("http://localhost:8080/api/attribute/{id}", request,params);
	}
	@Test
	public void shouldThrowExceptionWhenTryToDeleteAttribute() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.ATTRIBUTE_NOT_FOUND, HttpStatus.NOT_FOUND));
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",89);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete("http://localhost:8080/api/attribute/{id}",params);
	}
	
	
	
	private CreateAttributeDto getAttributeDto() {
		CreateAttributeDto createAttributeDto = new CreateAttributeDto();
		createAttributeDto.setNameAttribute("username");
		createAttributeDto.setTypeAttribute(AttributesTypeEnum.String);
		return createAttributeDto;
	}

}
