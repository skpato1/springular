package com.sifast.springular.framework.business.logic.web.service.api;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface IBuisnessLogicEntityApi {

    @ApiResponses(value = {
            @ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.BUISNESS_LOGIC_ENTITY_CREATED_SUCCESSFULLY, response = ViewBuisnessLogicEntityDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "create an entity ", response = ViewProjectDto.class)
    @PostMapping(value = "/buisnessLogicEntity")
    ResponseEntity<?> saveBuisnessLogicEntity(CreateBuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult);

    @GetMapping(value = "/buisnessLogicEntity/{id}")
    ResponseEntity<?> getBuisnessLogicEntity(int id);

    @GetMapping(value = "/buisnessLogicEntitys")
    ResponseEntity<?> getAllBuisnessLogicEntitys();

    @GetMapping(value = "/buisnessLogicEntity/project/{id}")
    ResponseEntity<?> getBuisnessLogicEntityByProject(int id);

    @DeleteMapping(value = "/buisnessLogicEntity/{id}")
    ResponseEntity<?> deleteBuisnessLogicEntity(int id);

    @PutMapping(value = "/buisnessLogicEntity/{id}")
    ResponseEntity<?> updateBuisnessLogicEntity(int id, BuisnessLogicEntityDto buisnessLogicEntityDto, BindingResult bindingResult);

}
