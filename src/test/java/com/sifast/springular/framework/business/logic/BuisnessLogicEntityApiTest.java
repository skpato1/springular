package com.sifast.springular.framework.business.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.CustomMatcher;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntityDto;
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

public class BuisnessLogicEntityApiTest extends SpringularFrameworkBusinessLogicApplicationTests {

    private CreateBuisnessLogicEntityDto createBuisnessLogicEntityDto;

    @Before
    public void setUp() {
        createBuisnessLogicEntityDto = getBuisnessLogicEntityDto();
    }


    @Test
    public void shouldThrowExceptionWhenTryToGetInexistantBuisnessLogicEntity() throws IOException {
        expectedEx.expect(HttpClientErrorException.class);
        expectedEx.expect(CustomMatcher.hasError(ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND));
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> template = restTemplate.getForEntity("http://localhost:8080/api/buisnessLogicEntity/" + 188, BuisnessLogicEntity.class);

    }

    @Test
    public void shouldThrowExceptionWhenTryToSaveBuisnessLogicEntity() throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CreateBuisnessLogicEntityDto> request = new HttpEntity<>(createBuisnessLogicEntityDto);
        ViewBuisnessLogicEntityDto BuisnessLogicEntityFromWebService = restTemplate
                .postForObject("http://localhost:8080/api/buisnessLogicEntity", request, ViewBuisnessLogicEntityDto.class);
        assertNotNull(BuisnessLogicEntityFromWebService);
        assertEquals(BuisnessLogicEntityFromWebService.getNameEntity(), createBuisnessLogicEntityDto.getNameEntity());
    }

    @Test
    public void shouldThrowExceptionWhenTryToUpdateBuisnessLogicEntity() throws IOException {
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 1);
        BuisnessLogicEntityDto updatedBuisnessLogicEntityDto = getBuisnessLogicEntityDto();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<BuisnessLogicEntityDto> request = new HttpEntity<>(updatedBuisnessLogicEntityDto);
        restTemplate.put("http://localhost:8080/api/buisnessLogicEntity/{id}", request, params);
    }

    @Test
    public void shouldThrowExceptionWhenTryToDeleteBuisnessLogicEntity() throws IOException {
        expectedEx.expect(HttpClientErrorException.class);
        expectedEx.expect(CustomMatcher.hasError(ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND, HttpStatus.NOT_FOUND));
        Map<String, Integer> params = new HashMap<String, Integer>();
        params.put("id", 89);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete("http://localhost:8080/api/buisnessLogicEntity/{id}", params);
    }


    private CreateBuisnessLogicEntityDto getBuisnessLogicEntityDto() {
        CreateBuisnessLogicEntityDto createBuisnessLogicEntityDto = new CreateBuisnessLogicEntityDto();
        createBuisnessLogicEntityDto.setNameEntity("User");
        return createBuisnessLogicEntityDto;
    }
}
