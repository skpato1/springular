package com.sifast.convertToJava.tn.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sifast.convertToJava.tn.service.CommandExecutorService;

@CrossOrigin("*")
@RestController
@RequestMapping("api/cmd/")
public class CommandExecutorController {

	@Autowired
	CommandExecutorService commandExecutorService;

	@GetMapping("github/{projectName}/{userName}")
	public void generateGithubProject(@PathVariable String projectName, @PathVariable String userName)
			throws IOException, InterruptedException {
		commandExecutorService.generateGithubProjectFromAngular(projectName, userName);
	}

	@GetMapping("database/{typeDB}/{nameDB}/{usernameDB}/{pwdDB}")
	public void generateDatabase(@PathVariable String typeDB, @PathVariable String nameDB,
			@PathVariable String usernameDB, @PathVariable String pwdDB) throws IOException, InterruptedException {
		commandExecutorService.generateApplicationPropertiesFromAngular(typeDB, nameDB, usernameDB, pwdDB);
	}

	@GetMapping("createDB/{nameDB}")
	public void createDatabase(@PathVariable String nameDB) throws IOException, InterruptedException {
		commandExecutorService.createDataBase(nameDB);
	}

}
