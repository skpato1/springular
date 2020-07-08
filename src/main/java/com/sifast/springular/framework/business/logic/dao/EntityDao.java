package com.sifast.springular.framework.business.logic.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Repository
public interface EntityDao extends IGenericDao<BuisnessLogicEntity, Integer> {

    Optional<BuisnessLogicEntity> findByNameEntityAndProject(String name, Project project);

    List<BuisnessLogicEntity> findByProject(Project project);

}
