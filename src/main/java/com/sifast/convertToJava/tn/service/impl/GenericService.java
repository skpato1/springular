package com.sifast.convertToJava.tn.service.impl;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.sifast.convertToJava.tn.common.Constants;
import com.sifast.convertToJava.tn.dao.IGenericDao;
import com.sifast.convertToJava.tn.service.IGenericService;


public class GenericService<T, P extends Serializable> implements IGenericService<T, P> {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(Constants.PATTERN_DATE);

    @Autowired
    private IGenericDao<T, P> genericDao;

    @Override
    public <S extends T> S save(S entity) {
        return genericDao.save(entity);
    }

	@Override
    public T update(T entity) {
        return genericDao.save(entity);
    }

	@Override
    public void delete(T entity) {
        genericDao.delete(entity);
    }

	@Override
    public List<T> findAll() {
        return genericDao.findAll();
    }

	@Override
    public List<T> findAllById(Iterable<P> ids) {
        return genericDao.findAllById(ids);
    }

	@Override
    public List<T> findAll(Sort sort) {
        return genericDao.findAll(sort);
    }
	@Override
    public Page<T> findAll(Pageable pageable) {
        return genericDao.findAll(pageable);
    }
	@Override
    public boolean existsById(P id) {
        return genericDao.existsById(id);
    }

	 @Override
	    public void deleteById(P id) {
	        genericDao.deleteById(id);
	    }

	 @Override
	    public void deleteAll() {
	        genericDao.deleteAll();
	    }

	 @Override
	    public T getOne(P id) {
	        return genericDao.getOne(id);
	    }

	@Override
	public Optional<T> findById(P id) {
		
        Optional<T> returnedObject;
        returnedObject = genericDao.findById(id);
        return returnedObject;

	}




   
   
}

