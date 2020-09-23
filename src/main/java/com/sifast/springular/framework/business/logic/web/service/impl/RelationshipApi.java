package com.sifast.springular.framework.business.logic.web.service.impl;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.service.IBuisnessLogicEntityService;
import com.sifast.springular.framework.business.logic.service.IRelationshipService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.relationship.CreateRelationshipDto;
import com.sifast.springular.framework.business.logic.web.dto.relationship.ViewRelationshipDto;
import com.sifast.springular.framework.business.logic.web.mapper.RelationshipMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IRelationshipApi;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/api/")
public class RelationshipApi implements IRelationshipApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipApi.class);

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

    @Autowired
    private ConfiguredModelMapper modelMapper;

    @Autowired
    private IRelationshipService relationshipService;

    @Autowired
    private IBuisnessLogicEntityService entityService;

    @Autowired
    private RelationshipMapper relationshipMapper;

    @Override
    public ResponseEntity<?> saveRelationship(@ApiParam(required = true, value = "relationshipDto", name = "relationshipDto") @RequestBody CreateRelationshipDto relationshipDto,
            BindingResult bindingResult) {
        LOGGER.info("Web service saveRelationship invoked with projectDto {}", relationshipDto);
        int parentEntityId = relationshipDto.getParentEntityId();
        int childEntityId = relationshipDto.getChildEntityId();

        try {
            Optional<BuisnessLogicEntity> parentEntity = entityService.findById(parentEntityId);
            Optional<BuisnessLogicEntity> childEntity = entityService.findById(childEntityId);
            if (parentEntity.isPresent() && childEntity.isPresent()) {
                Relationship relationshipToBeSaved = relationshipMapper.mapCreateRelationship(relationshipDto);
                relationshipToBeSaved.setParentEntity(parentEntity.get());
                relationshipToBeSaved.setChildEntity(childEntity.get());
                Relationship savedRelationship = relationshipService.save(relationshipToBeSaved);
                httpStatus = HttpStatus.OK;
                ViewRelationshipDto relationshipToBeReturned = modelMapper.map(savedRelationship, ViewRelationshipDto.class);
                httpResponseBody = relationshipToBeReturned;
            } else {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            }

        } catch (Exception e) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            httpResponseBody = httpErrorResponse;
        }

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getRelationship(
            @ApiParam(value = "ID of Relationship that needs to be fetched", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service getRelationship invoked with id {}", id);

        Optional<Relationship> relationship = relationshipService.findById(id);
        if (relationship.isPresent()) {
            httpStatus = HttpStatus.OK;
            httpResponseBody = relationshipMapper.mapRelationshipToViewRelationshipDto(relationship.get());
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.RELATIONSHIP_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getAllRelationshipsByProject(
            @ApiParam(value = "ID of Project", required = true, allowableValues = "range[1,infinity]") @PathVariable("projectId") int projectId) {
        List<Relationship> relationships = relationshipService.findAll();
        httpStatus = HttpStatus.OK;
        httpResponseBody = !relationships.isEmpty() ? relationships.stream().map(relationship -> modelMapper.map(relationship, ViewRelationshipDto.class))
                .filter(element -> element.getChildEntity().getProject().getId() == projectId).collect(Collectors.toList()) : Collections.emptyList();
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> deleteRelationship(
            @ApiParam(value = "ID of Relationship that needs to be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service deleteRelationship invoked with id {}", id);
        Optional<Relationship> preDeleteRelationship = relationshipService.findById(id);
        if (!preDeleteRelationship.isPresent()) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.RELATIONSHIP_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            relationshipService.delete(preDeleteRelationship.get());
            httpStatus = HttpStatus.OK;
            LOGGER.info("INFO level message: Relationship with id = {} deleted ", id);
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

}
