package com.sifast.springular.framework.business.logic.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.Database;

@Transactional
public interface IDatabaseService extends IGenericService<Database, Integer> {

}
