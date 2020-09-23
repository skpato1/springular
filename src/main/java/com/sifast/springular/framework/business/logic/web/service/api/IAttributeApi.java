package com.sifast.springular.framework.business.logic.web.service.api;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.attribute.AttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.CreateAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.attribute.ViewAttributeDto;
import com.sifast.springular.framework.business.logic.web.dto.project.ViewProjectDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface IAttributeApi {

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.ATTRIBUTE_CREATED_SUCCESSFULLY, response = ViewAttributeDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "create an attribute ", response = ViewProjectDto.class)
    @PostMapping(value = "/attribute")
    ResponseEntity<?> saveAttribute(CreateAttributeDto attributeDto, BindingResult bindingResult);

    @ApiResponses(value = {
            @ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.ATTRIBUTE_CREATED_SUCCESSFULLY, response = ViewAttributeDto.class, responseContainer = "List"),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "create multiple attributes ", response = ViewProjectDto.class)
    @PostMapping(value = "/attributes")
    ResponseEntity<?> saveAttributes(Integer entityId, List<CreateAttributeDto> attributeDtos, BindingResult bindingResult);

    @GetMapping(value = "/attribute/{id}")
    ResponseEntity<?> getAttribute(int id);

    @GetMapping(value = "/attributes")
    ResponseEntity<?> getAllAttributes();

    @DeleteMapping(value = "/attribute/{id}")
    ResponseEntity<?> deleteAttribute(int id);

    @PutMapping(value = "/attribute/{id}")
    ResponseEntity<?> updateAttribute(int id, AttributeDto attributeDto, BindingResult bindingResult);

    @GetMapping(value = "/attributes/types")
    ResponseEntity<?> getAttributesTypes();

}
