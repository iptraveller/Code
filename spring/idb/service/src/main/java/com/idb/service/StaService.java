package com.idb.service;

import com.idb.dao.mapper.StaMapper;
import com.idb.entity.Oui;
import com.idb.entity.Project;
import com.idb.entity.Sta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StaService {
    @Autowired
    private StaMapper staMapper;

    public Sta getStaByMac(String mac) {
        Sta sta = staMapper.getByMac(mac);
        if (sta != null) {
            Oui oui = staMapper.getOui(mac.substring(0, 7));
            if (oui != null) {
                sta.setManufacturerEn(oui.getManufacturerEn());
            }
        }
        return sta;
    }

    public List<Sta> getStaList(String mac, int start, int length) {
        if (mac.equals("")) {
            return staMapper.getListAll(start, length);
        } else {
            mac = mac + '%';
            return staMapper.getListLike(mac, start, length);
        }
    }

    public Integer getStaCount(String mac) {
        if (mac.equals("")) {
            return staMapper.getCountAll();
        } else {
            mac = mac + '%';
            return staMapper.getCountLike(mac);
        }
    }

    public Oui getStaOui(String mac) {
        return staMapper.getOui(mac.substring(0, 7));
    }

    public Map<String, Object> getStaLocation(String mac) {
        return staMapper.getStaLocation(mac);
    }

    public Project getStaProject(String id) {
        return staMapper.getProject(id);
    }

    public List<Map<String, Object>> getStaCountList() {
        return staMapper.getStaCountList();
    }

    public List<Map<String, Object>> getStaActiveCountList() {
        return staMapper.getStaActiveCountList();
    }

    public List<Map<String, Object>> getStaIndustryList() { return staMapper.getStaIndustryList(); }

    public List<Map<String, Object>> getStaCountProject() { return staMapper.getStaCountProject(); }

    public List<Map<String, Object>> getStaCountIndustry() {
        return staMapper.getStaCountIndustry();
    }

    public List<Map<String, Object>> getStaCountProvince() {
        return staMapper.getStaCountProvince();
    }

    public List<Map<String, Object>> getSta5gCount() {
        return staMapper.getSta5gCount();
    }

    public List<Map<String, Object>> getStaOsCount() {
        return staMapper.getStaOsCount();
    }

    public List<Map<String, Object>> getStaClientTypeCount() {
        return staMapper.getStaClientTypeCount();
    }

    public List<Map<String, Object>> getManufacturerCount() {
        return staMapper.getManufacturerCount();
    }

    public Map<String, Integer> getCapacibility() {
        Integer is11a = staMapper.get11aCount();
        Integer is11b = staMapper.get11bCount();
        Integer is11g = staMapper.get11gCount();
        Integer is11an = staMapper.get11anCount();
        Integer is11bn = staMapper.get11bnCount();
        Integer is11ac = staMapper.get11acCount();
        Integer is11acWave2 = staMapper.get11acWave2Count();

        Map<String, Integer> map = new HashMap<>();
        map.put("is11a", is11a);
        map.put("is11b", is11b);
        map.put("is11g", is11g);
        map.put("is11an", is11an);
        map.put("is11bn", is11bn);
        map.put("is11ac", is11ac);
        map.put("is11acWave2", is11acWave2);

        return map;
    }
}
