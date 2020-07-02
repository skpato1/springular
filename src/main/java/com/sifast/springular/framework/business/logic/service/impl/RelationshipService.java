package com.sifast.springular.framework.business.logic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.dao.RelationshipDao;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Relationship;
import com.sifast.springular.framework.business.logic.service.IRelationshipService;

@Service
public class RelationshipService extends GenericService<Relationship, Integer> implements IRelationshipService {

    @Autowired
    RelationshipDao relationshipDao;

    @Override
    public List<Relationship> findByParentEntity(BuisnessLogicEntity parentEntity) {
        return relationshipDao.findByParentEntity(parentEntity);
    }

    @Override
    public List<Relationship> findByChildEntity(BuisnessLogicEntity childEntity) {
        return relationshipDao.findByChildEntity(childEntity);
    }

}
