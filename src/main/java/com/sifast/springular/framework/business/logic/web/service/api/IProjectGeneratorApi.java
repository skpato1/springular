package com.sifast.springular.framework.business.logic.web.service.api;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;

public interface IProjectGeneratorApi {

    @GetMapping(value = "/generation/project/{id}", produces = "application/zip")
    byte[] zipAndDownloadProject(int id, HttpServletResponse response) throws IOException, InterruptedException;

}
