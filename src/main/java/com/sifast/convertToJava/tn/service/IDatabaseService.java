package com.sifast.convertToJava.tn.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.convertToJava.tn.entities.Database;

@Transactional
public interface IDatabaseService extends IGenericService<Database, Integer> {

}
