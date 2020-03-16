package com.sifast.springular.framework.business.logic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.springular.framework.business.logic.service.IEntityGeneratorService;

@CrossOrigin("*")
@RestController
@RequestMapping("entity/")
public class EntityGeneratorController {

	@Autowired
	IEntityGeneratorService entityGeneratorService;

	@GetMapping("model/{className}/{fieldName}/{fieldType}")
	public void generateFileDynamicWithArrayAngular(@PathVariable String className, @PathVariable List<String> fieldName,
			@PathVariable List<String> fieldType) {
		entityGeneratorService.generateEntityDynamicWithArrayAngular(className, fieldName, fieldType);
	}
	
	@GetMapping("get")
	public void getApi() {
		
		System.out.println("sss");

	}

}
