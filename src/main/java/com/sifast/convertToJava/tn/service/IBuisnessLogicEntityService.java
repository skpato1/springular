package com.sifast.convertToJava.tn.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.convertToJava.tn.entities.BuisnessLogicEntity;

@Transactional
public interface IBuisnessLogicEntityService extends IGenericService<BuisnessLogicEntity,Integer> {

}
