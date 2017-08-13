package com.idb.service;

import com.idb.entity.Menu;
import com.idb.entity.TreeModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {
    public List<Menu> getNavMenus() {
        List<Menu> list = new ArrayList<>();
        list.add(new Menu("sta","0","终端信息","/sta"));
        list.add(new Menu("sta-list","0,sta","终端查询","/sta/list"));
        list.add(new Menu("sta-statistics","0,sta","终端统计","/sta/statistics"));
//        list.add(new Menu("device","0","设备信息","/device"));
//        list.add(new Menu("device-list","0,device","设备查询","/device/list"));
//        list.add(new Menu("device-statistics","0,device","设备统计","/device/statistics"));
        return (List<Menu>) TreeModel.buildTree(list);
    }
}

