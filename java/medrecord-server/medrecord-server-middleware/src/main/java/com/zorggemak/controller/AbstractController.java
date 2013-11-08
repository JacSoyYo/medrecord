package com.zorggemak.controller;

import com.medvision360.medrecord.engine.MedRecordEngine;
import com.medvision360.medrecord.spi.exceptions.InitializationException;
import com.zorggemak.commons.MiddlewareErrors;
import com.zorggemak.util.WebUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public abstract class AbstractController {
    private final static Log log = LogFactory.getLog(AbstractController.class);
    
    private final static MedRecordEngine engine = new MedRecordEngine();

    public MedRecordEngine engine() throws InitializationException
    {
        engine.initialize();
        return engine;
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error(e.getMessage(), e);
        HashMap result = WebUtils.middlewareException(request, MiddlewareErrors.SERVER_EXCEPTION, e.getMessage(), e);
        String body = WebUtils.createJsonString(result);
        response.setContentType("application/json");
        try {
            PrintWriter w = response.getWriter();
            w.print(body);
            w.flush();
        } catch (IOException ioe) {
            // ignore
        }
    }
}
