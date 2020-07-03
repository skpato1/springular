package com.sifast.springular.framework.business.logic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;

@Transactional
public interface IBuisnessLogicEntityService extends IGenericService<BuisnessLogicEntity, Integer> {

    Optional<BuisnessLogicEntity> findbyNameEntityAndProject(String name, Project project);

    Optional<List<BuisnessLogicEntity>> findbyProject(Project project);

}
