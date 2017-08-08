package com.idb.service;

import com.idb.dao.mapper.TestMapper;
import com.idb.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestService {
    @Autowired
    private TestMapper testMapper;

    public List<Test> getTests() { return testMapper.getTests(); }
}
