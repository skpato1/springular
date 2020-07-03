package com.sifast.springular.framework.business.logic.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifast.springular.framework.business.logic.dao.EntityDao;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IBuisnessLogicEntityService;

@Service
public class BuisnessLogicEntityService extends GenericService<BuisnessLogicEntity, Integer> implements IBuisnessLogicEntityService

{

    @Autowired
    EntityDao entityDao;

    @Override
    public Optional<BuisnessLogicEntity> findbyNameEntityAndProject(String name, Project project) {
        return entityDao.findByNameEntityAndProject(name, project);
    }

    @Override
    public Optional<List<BuisnessLogicEntity>> findbyProject(Project project) {
        return entityDao.findByProject(project);
    }

}
