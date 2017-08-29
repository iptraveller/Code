package com.idb.dao.mapper;

import com.idb.entity.Oui;
import com.idb.entity.Project;
import com.idb.entity.Sta;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StaMapper {
    @Select("SELECT COUNT(*) FROM sta_full")
    Integer getCountAll();

    @Select("SELECT COUNT(*) FROM sta_full WHERE mac LIKE #{mac}")
    Integer getCountLike(@Param("mac") String mac);

    @Select("SELECT * FROM sta_full WHERE mac = #{mac}")
    @Results({
            @Result(property = "mac", column = "mac", javaType = String.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "os", column = "os", javaType = String.class),
            @Result(property = "osVersion", column = "os_version", javaType = String.class),
            @Result(property = "model", column = "model", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "is5g", column = "is_5g", javaType = Integer.class),
            @Result(property = "is11a", column = "is_11a", javaType = Integer.class),
            @Result(property = "is11b", column = "is_11b", javaType = Integer.class),
            @Result(property = "is11g", column = "is_11g", javaType = Integer.class),
            @Result(property = "is11an", column = "is_11an", javaType = Integer.class),
            @Result(property = "is11bn", column = "is_11bn", javaType = Integer.class),
            @Result(property = "is11ac", column = "is_11ac", javaType = Integer.class),
            @Result(property = "is11acWave2", column = "is_11ac_wave2", javaType = Integer.class),
            @Result(property = "firstOnlineTime", column = "first_online_time", javaType = Date.class),
            @Result(property = "lastOnlineTime", column = "last_online_time", javaType = Date.class),
            @Result(property = "projectId", column = "project_id", javaType = String.class),
            @Result(property = "source", column = "source", javaType = Integer.class)
    })
    Sta getByMac(String mac);

    @Select("SELECT * FROM sta_full ORDER BY mac LIMIT #{start},#{length}")
    @Results({
            @Result(property = "mac", column = "mac", javaType = String.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "os", column = "os", javaType = String.class),
            @Result(property = "osVersion", column = "os_version", javaType = String.class),
            @Result(property = "model", column = "model", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "is5g", column = "is_5g", javaType = Integer.class),
            @Result(property = "is11a", column = "is_11a", javaType = Integer.class),
            @Result(property = "is11b", column = "is_11b", javaType = Integer.class),
            @Result(property = "is11g", column = "is_11g", javaType = Integer.class),
            @Result(property = "is11an", column = "is_11an", javaType = Integer.class),
            @Result(property = "is11bn", column = "is_11bn", javaType = Integer.class),
            @Result(property = "is11ac", column = "is_11ac", javaType = Integer.class),
            @Result(property = "is11acWave2", column = "is_11ac_wave2", javaType = Integer.class),
            @Result(property = "firstOnlineTime", column = "first_online_time", javaType = Date.class),
            @Result(property = "lastOnlineTime", column = "last_online_time", javaType = Date.class),
            @Result(property = "projectId", column = "project_id", javaType = String.class),
            @Result(property = "source", column = "source", javaType = Integer.class)
    })
    List<Sta> getListAll(@Param("start") Integer start, @Param("length")  Integer length);

    @Select("SELECT * FROM sta_full WHERE mac LIKE #{mac} ORDER BY mac LIMIT #{start},#{length}")
    @Results({
            @Result(property = "mac", column = "mac", javaType = String.class),
            @Result(property = "username", column = "username", javaType = String.class),
            @Result(property = "os", column = "os", javaType = String.class),
            @Result(property = "osVersion", column = "os_version", javaType = String.class),
            @Result(property = "model", column = "model", javaType = String.class),
            @Result(property = "type", column = "type", javaType = String.class),
            @Result(property = "is5g", column = "is_5g", javaType = Integer.class),
            @Result(property = "is11a", column = "is_11a", javaType = Integer.class),
            @Result(property = "is11b", column = "is_11b", javaType = Integer.class),
            @Result(property = "is11g", column = "is_11g", javaType = Integer.class),
            @Result(property = "is11an", column = "is_11an", javaType = Integer.class),
            @Result(property = "is11bn", column = "is_11bn", javaType = Integer.class),
            @Result(property = "is11ac", column = "is_11ac", javaType = Integer.class),
            @Result(property = "is11acWave2", column = "is_11ac_wave2", javaType = Integer.class),
            @Result(property = "firstOnlineTime", column = "first_online_time", javaType = Date.class),
            @Result(property = "lastOnlineTime", column = "last_online_time", javaType = Date.class),
            @Result(property = "projectId", column = "project_id", javaType = String.class),
            @Result(property = "source", column = "source", javaType = Integer.class)
    })
    List<Sta> getListLike(@Param("mac") String mac,@Param("start") Integer start,@Param("length")  Integer length);

    @Select("SELECT * FROM oui WHERE oui = #{oui}")
    @Results({
            @Result(property = "oui", column = "oui", javaType = String.class),
            @Result(property = "manufacturerEn", column = "manufacturer_en", javaType = String.class)
    })
    Oui getOui(String oui);

    @Select("SELECT * FROM project WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id", javaType = String.class),
            @Result(property = "name", column = "name", javaType = String.class),
            @Result(property = "industry", column = "industry", javaType = String.class),
            @Result(property = "province", column = "province", javaType = String.class),
            @Result(property = "country", column = "country", javaType = String.class),
    })
    Project getProject(String id);

    @Select("SELECT * FROM sta_count WHERE TO_DAYS(NOW()) - TO_DAYS(date) <= 100 and TO_DAYS(NOW()) - TO_DAYS(date) > 0 ORDER BY date")
    @Results({
            @Result(property = "date", column = "date", javaType = Date.class),
            @Result(property = "wisCount", column = "wis_count", javaType = Integer.class),
            @Result(property = "mohooCount", column = "mohoo_count", javaType = Integer.class),
            @Result(property = "wisTotalCount", column = "wis_total_count", javaType = Integer.class),
            @Result(property = "mohooTotalCount", column = "mohoo_total_count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaCountList();

    @Select("select date,count(*) as count from sta_active where TO_DAYS(NOW()) - TO_DAYS(date) > 0 group by date order by date")
    @Results({
            @Result(property = "date", column = "date", javaType = Date.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaActiveCountList();

    @Select("select date,project.industry,sum(total_count) as count from sta_count_project inner join project on sta_count_project.project_id = project.id WHERE TO_DAYS(NOW()) - TO_DAYS(date) <= 30 group by date,industry order by industry,date;")
    @Results({
            @Result(property = "date", column = "date", javaType = Date.class),
            @Result(property = "industry", column = "industry", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaIndustryList();

    @Select("select name,sum(count) as count from sta_count_project inner join project on sta_count_project.project_id = project.id group by project_id order by count desc limit 20;")
    @Results({
            @Result(property = "projectName", column = "name", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaCountProject();

    @Select("select industry,sum(count) as count from (select project_id,name,industry,sum(count) as count from sta_count_project inner join project on sta_count_project.project_id = project.id group by project_id) as t1 group by industry order by count desc;")
    @Results({
        @Result(property = "industry", column = "industry", javaType = String.class),
        @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaCountIndustry();

    @Select("select province,sum(count) as count from (select project_id,name,province,sum(count) as count from sta_count_project inner join project on sta_count_project.project_id = project.id group by project_id) as t1 group by province order by count desc;")
    @Results({
            @Result(property = "province", column = "province", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaCountProvince();

    @Select("SELECT * FROM sta_band_count")
    @Results({
            @Result(property = "is5g", column = "band", javaType = Integer.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getSta5gCount();

    @Select("SELECT * FROM sta_os_count")
    @Results({
            @Result(property = "os", column = "os", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaOsCount();

    @Select("SELECT * FROM sta_client_type_count")
    @Results({
            @Result(property = "clientType", column = "type", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getStaClientTypeCount();

    @Select("select manufacturer_short,sum(count) as count from (select oui,manufacturer_short,count from sta_oui_count inner join oui using(oui)) as t1 group by manufacturer_short order by count desc")
    @Results({
            @Result(property = "manufacturer", column = "manufacturer_short", javaType = String.class),
            @Result(property = "count", column = "count", javaType = Integer.class),
    })
    List<Map<String, Object>> getManufacturerCount();

    @Select("SELECT province,city FROM sta_location WHERE mac = #{mac}")
    @Results({
            @Result(property = "province", column = "province", javaType = String.class),
            @Result(property = "city", column = "city", javaType = String.class)
    })
    Map<String, Object> getStaLocation(String mac);
}
