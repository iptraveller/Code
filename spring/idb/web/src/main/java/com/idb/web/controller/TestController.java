package com.idb.web.controller;

import com.idb.entity.Test;
import com.idb.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Resource
    TestService testService;

    @GetMapping("/test")
    public List<Map<String, Object>> test() {
        return testService.getTests();
    }
}
