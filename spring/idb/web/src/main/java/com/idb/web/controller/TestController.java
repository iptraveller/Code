package com.idb.web.controller;

import com.idb.entity.Test;
import com.idb.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
    @Resource
    TestService testService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/test")
    public List<Map<String, Object>> test() {
        return testService.getTests();
    }

    @GetMapping("/redis")
    public String redis() {
        Test test = new Test();
        test.setKey("key");
        test.setValue("value");
        ValueOperations<String, Test> operations = redisTemplate.opsForValue();
        operations.set("key", test);
        return "ok";
    }

    @GetMapping("/get")
    public String get() {
        ValueOperations<String, Test> operations = redisTemplate.opsForValue();
        Test test = operations.get("key");
        return test.getValue();
    }

}
