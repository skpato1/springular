package com.sifast.convertToJava.tn.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.convertToJava.tn.service.EntityGeneratorService;

@CrossOrigin("*")
@RestController
@RequestMapping("api/entity/")
public class EntityGeneratorController {

	@Autowired
	EntityGeneratorService entityGeneratorService;

	@GetMapping("automatic/{packageName}/{className}/{projectName}/{fieldName}/{fieldType}")
	public void generateFileDynamic(@PathVariable String packageName, @PathVariable String className,
			@PathVariable String projectName, @PathVariable String fieldName, @PathVariable String fieldType) {
		entityGeneratorService.generateEntityDynamic(packageName, className, projectName, fieldName, fieldType);
		System.out.println("file Dynamic generated");
	}

	@GetMapping("replace/{packageName}/{projectName}")
	public void replaceString(@PathVariable String packageName, @PathVariable String projectName) throws IOException {
		entityGeneratorService.replaceString(packageName, projectName);
		System.out.println("package replaced");

	}

	@GetMapping("array/{packageName}/{className}/{projectName}/{fieldName}/{fieldType}")
	public void generateFileDynamicWithArray(@PathVariable String packageName, @PathVariable String className,
			@PathVariable String projectName, @PathVariable String fieldName[], @PathVariable String fieldType[]) {
		entityGeneratorService.generateEntityDynamicWithArray(packageName, className, projectName, fieldName,
				fieldType);
		System.out.println("file Dynamic generated with array");
	}
	@GetMapping("model/{className}/{fieldName}/{fieldType}")
	public void generateFileDynamicWithArrayAngular(@PathVariable String className, @PathVariable String fieldName[], @PathVariable String fieldType[]) {
		entityGeneratorService.generateEntityDynamicWithArrayAngular(className, fieldName,fieldType);
		System.out.println("file Dynamic generated with array Angular");
	}

}
