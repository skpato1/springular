package com.sifast.springular.framework.business.logic.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Relationship;

@Transactional
public interface IRelationshipService extends IGenericService<Relationship, Integer> {

    List<Relationship> findByParentEntity(BuisnessLogicEntity parentEntity);

    List<Relationship> findByChildEntity(BuisnessLogicEntity childEntity);

}
