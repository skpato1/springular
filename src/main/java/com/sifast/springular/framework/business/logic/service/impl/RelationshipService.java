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
    public List<Relationship> findByMasterEntity(BuisnessLogicEntity masterEntity) {
        return relationshipDao.findByMasterEntity(masterEntity);
    }

    @Override
    public List<Relationship> findBySlaveEntity(BuisnessLogicEntity slaveEntity) {
        return relationshipDao.findBySlaveEntity(slaveEntity);
    }

}
