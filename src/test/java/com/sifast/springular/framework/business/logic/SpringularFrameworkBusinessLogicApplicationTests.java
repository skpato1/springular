package com.sifast.springular.framework.business.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.sifast.springular.framework.business.logic.common.AttributesTypeEnum;
import com.sifast.springular.framework.business.logic.entities.Attribute;
import com.sifast.springular.framework.business.logic.entities.BuisnessLogicEntity;
import com.sifast.springular.framework.business.logic.entities.Database;
import com.sifast.springular.framework.business.logic.entities.Project;
import com.sifast.springular.framework.business.logic.service.IAttributeService;
import com.sifast.springular.framework.business.logic.service.IBuisnessLogicEntityService;
import com.sifast.springular.framework.business.logic.service.IDatabaseService;
import com.sifast.springular.framework.business.logic.service.IProjectService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		SpringularFrameworkBusinessLogicApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public abstract class SpringularFrameworkBusinessLogicApplicationTests {

	
	@Autowired
	public IAttributeService attributeService;

	@Autowired
	public IProjectService projectService;

	@Autowired
	public IDatabaseService databaseService;

	@Autowired
	public IBuisnessLogicEntityService buisnessLogicEntityService;

	@Rule
	public ExpectedException expectedEx = ExpectedException.none();

	protected Project saveProject() {
		Project project = new Project();
		project.setNameProject("spring-test");
		project.setTypeProject("Monolotic");
		project.setPortProject("8080");
		List<BuisnessLogicEntity> entities = new ArrayList<>();
		entities.add(new BuisnessLogicEntity());
		project.setEntities(entities);
		project.setDatabase(saveDatabase());
		return projectService.save(project);
	}

	protected Database saveDatabase() {
		Database database = new Database();
		database.setNameDatabase("springDB");
		database.setTypeDatabase("Mysql");
		return databaseService.save(database);
	}

	protected Attribute saveAttribute() {
		Attribute attribute = new Attribute();
		attribute.setNameAttribute("springDB");
		attribute.setTypeAttribute(AttributesTypeEnum.String);
		return attributeService.save(attribute);
	}

	protected BuisnessLogicEntity saveBuisnessLogicEntity() {
		BuisnessLogicEntity buisnessLogicEntity = new BuisnessLogicEntity();
		buisnessLogicEntity.setNameEntity("User");
		List<Attribute> attributes = new ArrayList<>();
		attributes.add(new Attribute());
		buisnessLogicEntity.setAttributes(attributes);
		return buisnessLogicEntityService.save(buisnessLogicEntity);
	}

}
