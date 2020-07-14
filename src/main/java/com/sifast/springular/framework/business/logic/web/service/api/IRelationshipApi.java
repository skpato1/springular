package com.sifast.springular.framework.business.logic.web.service.api;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.relationship.CreateRelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.ViewRelationshipDto;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface IRelationshipApi {

    @ApiResponses(value = { @ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.RELATIONSHIP_CREATED_SUCCESSFULLY, response = ViewRelationshipDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class) })
    @ApiOperation(value = "save Relationship", response = ViewRelationshipDto.class)
    @RequestMapping(value = "/relationship", method = RequestMethod.POST)
    ResponseEntity<?> saveRelationship(CreateRelationshipDto relationShipDto, BindingResult bindingResult);

    @RequestMapping(value = "/relationship/{id}", method = RequestMethod.GET)
    ResponseEntity<?> getRelationship(int id);

    @RequestMapping(value = "/relationship/project/{projectId}", method = RequestMethod.GET)
    ResponseEntity<?> getAllRelationshipsByProject(int projectId);

    @RequestMapping(value = "/relationship/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteRelationship(int id);
}
