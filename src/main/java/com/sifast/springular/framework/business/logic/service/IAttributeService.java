package com.sifast.springular.framework.business.logic.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Attribute;

@Transactional
public interface IAttributeService extends IGenericService<Attribute, Integer> {

}
