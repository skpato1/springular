package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

public interface IAttributeApi {

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.ATTRIBUTE_CREATED_SUCCESSFULLY, response = ViewAttributeDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "create an attribute ", response = ViewProjectDto.class)
    @RequestMapping(value = "/attribute", method = RequestMethod.POST)
    ResponseEntity<?> saveAttribute(CreateAttributeDto attributeDto, BindingResult bindingResult);

    @RequestMapping(value = "/attribute/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getAttribute(int id);

    @RequestMapping(value = "/attributes", method = RequestMethod.GET)
    ResponseEntity<?> getAllAttributes();

    @RequestMapping(value = "/attribute/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteAttribute(int id);

    @RequestMapping(value = "/attribute/{id}", method = RequestMethod.PUT)
    ResponseEntity<?> updateAttribute(int id, AttributeDto attributeDto, BindingResult bindingResult);

}
