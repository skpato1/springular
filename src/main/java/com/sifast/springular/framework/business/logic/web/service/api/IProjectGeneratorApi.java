package com.sifast.springular.framework.business.logic.web.service.api;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface IProjectGeneratorApi {

    @RequestMapping(value = "/generation/project/{id}", method = RequestMethod.GET, produces = "application/zip")
    byte[] zipAndDownloadProject(int id, HttpServletResponse response) throws IOException, InterruptedException;

}
