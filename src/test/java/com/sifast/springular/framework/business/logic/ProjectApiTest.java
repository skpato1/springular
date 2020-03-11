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
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.web.dto.project.CreateProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ProjectDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;

public class ProjectApiTest extends SpringularFrameworkBusinessLogicApplicationTests {

	private CreateProjectDto createProjectDto;

	@Before
	public void setUp() {
		createProjectDto = getProjectDto();
	}
	
	@Test
	public void shouldThrowExceptionWhenTryToGetInexistantProject() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<?> template = restTemplate.getForEntity("http://localhost:8080/api/project/" + 188, Project.class);
				
	}
	@Test
	public void shouldThrowExceptionWhenTryToSaveProject() throws IOException{
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<CreateProjectDto> request = new HttpEntity<>(createProjectDto);
	    ViewProjectDto projectFromWebService = restTemplate.postForObject("http://localhost:8080/api/project", request, ViewProjectDto.class);
	    assertNotNull(projectFromWebService);
	    assertEquals(projectFromWebService.getNameProject(), createProjectDto.getNameProject());
	}
	
	@Test
	public void shouldThrowExceptionWhenTryToUpdateProject() throws IOException{
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",1);
	    ProjectDto updatedProjectDto = getProjectDto();
	    RestTemplate restTemplate = new RestTemplate();
	    HttpEntity<ProjectDto> request = new HttpEntity<>(updatedProjectDto);
	    restTemplate.put("http://localhost:8080/api/project/{id}", request,params);
	}
	@Test
	public void shouldThrowExceptionWhenTryToDeleteProject() throws IOException {
		expectedEx.expect(HttpClientErrorException.class);
		expectedEx.expect(CustomMatcher.hasError(ApiMessage.PROJECT_NOT_FOUND, HttpStatus.NOT_FOUND));
		Map<String, Integer> params = new HashMap<String, Integer>();
	    params.put("id",89);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete("http://localhost:8080/api/project/{id}",params);
	}
	
	
	private CreateProjectDto getProjectDto() {
		CreateProjectDto createProjectDto = new CreateProjectDto();
		createProjectDto.setNameProject("spring-test");
		createProjectDto.setPortProject("8080");
		createProjectDto.setTypeProject("Monolotic");
		return createProjectDto;
	}

}
