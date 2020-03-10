package com.sifast.convertToJava.tn.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Transactional
public interface IGenericService<T, P extends Serializable> {

	<S extends T> S save(S entity);

	T update(T entity);

	void delete(T entity);
	
    Optional<T> findById(P id);


	List<T> findAll();

	List<T> findAllById(Iterable<P> ids);

	List<T> findAll(Sort sort);

	Page<T> findAll(Pageable pageable);

	boolean existsById(P id);

	void deleteById(P id);

	void deleteAll();
	
    T getOne(P id);

}
