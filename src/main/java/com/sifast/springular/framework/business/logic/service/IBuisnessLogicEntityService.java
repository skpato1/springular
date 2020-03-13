package com.sifast.springular.framework.business.logic.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;

@Transactional
public interface IBuisnessLogicEntityService extends IGenericService<BuisnessLogicEntity,Integer> {

}
