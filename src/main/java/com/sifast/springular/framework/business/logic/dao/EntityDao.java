package com.sifast.springular.framework.business.logic.dao;

import org.springframework.stereotype.Repository;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;


@Repository
public interface EntityDao extends IGenericDao<BuisnessLogicEntity, Integer> {

}
