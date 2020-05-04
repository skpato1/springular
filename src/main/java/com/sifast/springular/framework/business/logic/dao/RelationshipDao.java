package com.sifast.springular.framework.business.logic.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Relationship;

@Repository
public interface RelationshipDao extends IGenericDao<Relationship, Integer> {

    List<Relationship> findByMasterEntity(BuisnessLogicEntity masterEntity);

    List<Relationship> findBySlaveEntity(BuisnessLogicEntity slaveEntity);

}
