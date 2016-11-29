package com.joinme.frontend.api.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Controller
public class IndexController {

    @Value("${joinme.frontend-api.cache-control.enabled}")
    private boolean caching;

    @RequestMapping(value = {"/", ""})
    public String index(HttpServletResponse response) {
        if (caching) {
            response.setHeader("Cache-Control",
                    CacheControl
                            .maxAge(1, TimeUnit.DAYS)
                            .cachePublic()
                            .getHeaderValue()
            );
        }
        return "forward:index.html";
    }
}
