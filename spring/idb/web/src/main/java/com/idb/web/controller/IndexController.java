package com.idb.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class IndexController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String index() {
        logger.error("test");
        return "index";
    }
}
