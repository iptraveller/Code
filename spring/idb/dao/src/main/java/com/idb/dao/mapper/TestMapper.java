package com.idb.dao.mapper;

import com.idb.entity.Test;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TestMapper {
    @Select("SELECT * FROM test")
    @Results({
            @Result(property = "TestKey", column = "key", javaType = String.class),
            @Result(property = "TestValue", column = "value", javaType = String.class)
    })
    List<Map<String, Object>> getTests();
}
