package com.sifast.springular.framework.business.logic.web.service.impl;

import com.sifast.springular.framework.business.logic.common.ApiMessage;
import com.sifast.springular.framework.business.logic.common.BusinessLogicException;
import com.sifast.springular.framework.business.logic.common.HttpCostumCode;
import com.sifast.springular.framework.business.logic.common.HttpErrorResponse;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IBuisnessLogicEntityService;
import com.sifast.springular.framework.business.logic.service.IProjectService;
import com.sifast.springular.framework.business.logic.web.config.ConfiguredModelMapper;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.BuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.CreateBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.dto.buisness_logic_entity.ViewBuisnessLogicEntityDto;
import com.sifast.springular.framework.business.logic.web.mapper.BuisnessLogicEntityMapper;
import com.sifast.springular.framework.business.logic.web.service.api.IBuisnessLogicEntityApi;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
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
public class BuisnessLogicEntityApi implements IBuisnessLogicEntityApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(BuisnessLogicEntityApi.class);

    private HttpErrorResponse httpErrorResponse = new HttpErrorResponse();

    private Object httpResponseBody;

    private HttpStatus httpStatus;

    @Autowired
    private ConfiguredModelMapper modelMapper;

    @Autowired
    private BuisnessLogicEntityMapper buisnessLogicEntityMapper;

    @Autowired
    private IBuisnessLogicEntityService buisnessLogicEntityService;

    @Autowired
    private IProjectService projectService;

    @Override
    public ResponseEntity<?> saveBuisnessLogicEntity(
            @ApiParam(required = true, value = "buisnessLogicEntityDto", name = "buisnessLogicEntityDto") @RequestBody CreateBuisnessLogicEntityDto buisnessLogicEntityDto,
            BindingResult bindingResult) {
        LOGGER.info("Web service saveBuisnessLogicEntity invoked with buisnessLogicEntityDto {}", buisnessLogicEntityDto);
        try {
            if (buisnessLogicEntityDto.getCreateListDtosIfChild().equals(buisnessLogicEntityDto.getCreateListIdsIfChild())) {
                throw new BusinessLogicException(ApiMessage.CHOICES_MUST_BE_DIFFERENT);
            }
            Optional<Project> project = projectService.findById(buisnessLogicEntityDto.getProjectId());
            if (project.isPresent()) {
                BuisnessLogicEntity entityToBeSaved = buisnessLogicEntityMapper.mapCreateBuisnessLogicEntity(buisnessLogicEntityDto);
                entityToBeSaved.setProject(project.get());
                if (entityToBeSaved.getAttributes() != null) {
                    List<Attribute> attributes = entityToBeSaved.getAttributes();
                    for (int i = 0; i < attributes.size(); i++) {
                        attributes.get(i).setBuisness(entityToBeSaved);
                    }
                    entityToBeSaved.setAttributes(attributes);
                }
                BuisnessLogicEntity savedBuisnessLogicEntity = buisnessLogicEntityService.save(entityToBeSaved);

                httpStatus = HttpStatus.OK;
                httpResponseBody = modelMapper.map(savedBuisnessLogicEntity, ViewBuisnessLogicEntityDto.class);
            } else {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            }
        } catch (BusinessLogicException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            httpResponseBody = new HttpErrorResponse(HttpCostumCode.BAD_REQUEST.getValue(), e.getMessage());
        } catch (Exception e) {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.SERVER_ERROR.getValue(), ApiMessage.ERR_SAVE);
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            httpResponseBody = httpErrorResponse;
        }

        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getBuisnessLogicEntity(
            @ApiParam(value = "ID of Entity that needs to be fetched", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service getBuisnessLogicEntity invoked with id {}", id);

        Optional<BuisnessLogicEntity> buisnessLogicEntity = buisnessLogicEntityService.findById(id);
        if (buisnessLogicEntity.isPresent()) {
            httpStatus = HttpStatus.OK;
            httpResponseBody = buisnessLogicEntityMapper.mapBuisnessLogicEntityToViewBuisnessLogicEntityDto(buisnessLogicEntity.get());
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getAllBuisnessLogicEntitys() {
        List<BuisnessLogicEntity> buisnessLogicEntitys = buisnessLogicEntityService.findAll();
        httpStatus = HttpStatus.OK;
        httpResponseBody = !buisnessLogicEntitys.isEmpty()
                ? buisnessLogicEntitys.stream().map(buisnessLogicEntity -> modelMapper.map(buisnessLogicEntity, ViewBuisnessLogicEntityDto.class)).collect(Collectors.toList())
                : Collections.emptyList();
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> deleteBuisnessLogicEntity(
            @ApiParam(value = "ID of Entity that needs to be deleted", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service deleteBuisnessLogicEntity invoked with id {}", id);
        Optional<BuisnessLogicEntity> preDeleteBuisnessLogicEntity = buisnessLogicEntityService.findById(id);
        if (!preDeleteBuisnessLogicEntity.isPresent()) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {

            buisnessLogicEntityService.delete(preDeleteBuisnessLogicEntity.get());
            httpStatus = HttpStatus.OK;
            LOGGER.info("INFO level message: BuisnessLogicEntity with id = {} deleted ", id);

        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> updateBuisnessLogicEntity(
            @ApiParam(value = "ID of Entity that needs to be updated", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id,
            @ApiParam(required = true, value = "BuisnessLogicEntityDto", name = "BuisnessLogicEntityDto") @RequestBody BuisnessLogicEntityDto buisnessLogicEntityDto,
            BindingResult bindingResult) {
        LOGGER.info("Web service updateBuisnessLogicEntity invoked with id {}", id);
        if (!bindingResult.hasFieldErrors()) {
            Optional<BuisnessLogicEntity> buisnessLogicEntity = buisnessLogicEntityService.findById(id);
            if (buisnessLogicEntity.isPresent()) {
                BuisnessLogicEntity preUpdateBuisnessLogicEntity = buisnessLogicEntity.get();
                BuisnessLogicEntity updatedBuisnessLogicEntity = buisnessLogicEntityService.save(preUpdateBuisnessLogicEntity);
                httpStatus = HttpStatus.OK;
                httpResponseBody = modelMapper.map(buisnessLogicEntity, BuisnessLogicEntity.class);
                LOGGER.info("INFO level message: BuisnessLogicEntity updated {}", updatedBuisnessLogicEntity);

            } else {
                httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.BUISNESS_LOGIC_ENTITY_NOT_FOUND);
                httpStatus = HttpStatus.NOT_FOUND;
                httpResponseBody = httpErrorResponse;
            }
        } else {
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

    @Override
    public ResponseEntity<?> getBuisnessLogicEntityByProject(
            @ApiParam(value = "ID of Project that needs to be fetched", required = true, allowableValues = "range[1,infinity]") @PathVariable("id") int id) {
        LOGGER.info("Web service getBuisnessLogicEntityByProject invoked with id {}", id);
        List<ViewBuisnessLogicEntityDto> entitiesToReturn = new ArrayList<>();
        Optional<Project> project = projectService.findById(id);
        if (project.isPresent()) {
            httpStatus = HttpStatus.OK;
            List<BuisnessLogicEntity> fetchedEntitiesByProject = buisnessLogicEntityService.findbyProject(project.get());
            fetchedEntitiesByProject.forEach(buisnessLogicEntity -> {
                ViewBuisnessLogicEntityDto mapBuisnessLogicEntityToViewBuisnessLogicEntityDto = buisnessLogicEntityMapper
                        .mapBuisnessLogicEntityToViewBuisnessLogicEntityDto(buisnessLogicEntity);
                entitiesToReturn.add(mapBuisnessLogicEntityToViewBuisnessLogicEntityDto);
            });
            httpResponseBody = entitiesToReturn;
        } else {
            httpErrorResponse.setHttpCodeAndMessage(HttpCostumCode.NOT_FOUND.getValue(), ApiMessage.PROJECT_NOT_FOUND);
            httpStatus = HttpStatus.NOT_FOUND;
            httpResponseBody = httpErrorResponse;
        }
        return new ResponseEntity<>(httpResponseBody, httpStatus);
    }

}
