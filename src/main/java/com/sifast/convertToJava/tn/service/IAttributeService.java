package com.sifast.convertToJava.tn.service;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.convertToJava.tn.entities.Attribute;

@Transactional
public interface IAttributeService extends IGenericService<Attribute, Integer> {

}
