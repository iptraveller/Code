package com.idb.dao.mapper;

import com.idb.entity.Test;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TestMapper {
    @Select("SELECT * FROM test")
    @Results({
            @Result(property = "key", column = "key", javaType = String.class),
            @Result(property = "value", column = "value", javaType = String.class)
    })
    List<Test> getTests();
}
