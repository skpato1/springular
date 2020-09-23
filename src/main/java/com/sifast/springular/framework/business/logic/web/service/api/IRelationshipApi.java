package com.sifast.springular.framework.business.logic.web.service.api;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.ApiStatus;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.web.dto.relationship.CreateRelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.ViewRelationshipDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

public interface IRelationshipApi {

    @ApiResponses(value = {@ApiResponse(code = ApiStatus.STATUS_ACCEPTED, message = ApiMessage.RELATIONSHIP_CREATED_SUCCESSFULLY, response = ViewRelationshipDto.class),
            @ApiResponse(code = ApiStatus.STATUS_BAD_REQUEST, message = ApiMessage.INVALID_INPUT, response = HttpErrorResponse.class)})
    @ApiOperation(value = "save Relationship", response = ViewRelationshipDto.class)
    @PostMapping(value = "/relationship")
    ResponseEntity<?> saveRelationship(CreateRelationshipDto relationShipDto, BindingResult bindingResult);

    @GetMapping(value = "/relationship/{id}")
    ResponseEntity<?> getRelationship(int id);

    @GetMapping(value = "/relationship/project/{projectId}")
    ResponseEntity<?> getAllRelationshipsByProject(int projectId);

    @DeleteMapping(value = "/relationship/{id}")
    ResponseEntity<?> deleteRelationship(int id);
}
