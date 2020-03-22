package com.sifast.springular.framework.business.logic.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IGenericDao<T, P extends Serializable> extends JpaRepository<T, P>, JpaSpecificationExecutor<T> {

	
}
