package com.sifast.springular.framework.business.logic.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Relationship;

@Transactional
public interface IRelationshipService extends IGenericService<Relationship, Integer> {

}
