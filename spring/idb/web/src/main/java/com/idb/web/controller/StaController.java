package com.idb.web.controller;

import com.idb.entity.Oui;
import com.idb.entity.Project;
import com.idb.entity.Sta;
import com.idb.service.StaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/sta")
public class StaController {
    @Resource
    private StaService staService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public String list(Model model) { return "sta/list"; }

    @RequestMapping(value="/statistics", method = RequestMethod.GET)
    public String statictics(Model model) {
        return "sta/statistics";
    }

    @RequestMapping(value="/getSta", method= RequestMethod.GET)
    public Sta getUsers(@RequestParam("mac") String mac) {
        System.out.println(mac);
        Sta sta =  staService.getStaByMac(mac);
        return sta;
    }

    @RequestMapping(value="/getStaList", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getStaList(HttpServletRequest request, HttpServletResponse response) {
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String search = request.getParameter("search[value]");
        search = search.trim();
        List<Sta> staList = staService.getStaList(search, Integer.parseInt(start), Integer.parseInt(length));
        int staNum = staService.getStaCount(search);

        Map<String,Object> map = new HashMap<>();
        map.put("draw",  request.getParameter("draw"));
        map.put("recordsTotal",  staNum);
        map.put("recordsFiltered",  staNum);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Sta sta:staList) {
            Map<String, Object> staMap = new HashMap<>();
            staMap.put("mac", sta.getMac());
            staMap.put("username", sta.getUsername());
            Oui oui = staService.getStaOui(sta.getMac());
            if (oui != null) {
                staMap.put("manufacturer", oui.getManufacturerEn());
            } else {
                staMap.put("manufacturer", "");
            }
            staMap.put("os", sta.getOs() + ' ' + sta.getOsVersion());
            staMap.put("model", sta.getModel());
            staMap.put("is5g", sta.getIs5g() == 1 ? "yes" : sta.getIs5g() == 2 ? "no" : "");
            if (sta.getIs11acWave2() == 1) {
                staMap.put("capacibility", "802.11ac_wave2");
            } else if (sta.getIs11ac() == 1) {
                staMap.put("capacibility", "802.11ac");
            } else if (sta.getIs11an() == 1 || sta.getIs11bn() == 1) {
                staMap.put("capacibility", "802.11n");
            } else if (sta.getIs11a() == 1) {
                staMap.put("capacibility", "802.11a");
            } else if (sta.getIs11b() == 1) {
                staMap.put("capacibility", "802.11b");
            } else if (sta.getIs11g() == 1) {
                staMap.put("capacibility", "802.11g");
            } else {
                staMap.put("capacibility","");
            }
            Project project = staService.getStaProject(sta.getProjectId());
            if (project != null) {
                staMap.put("location", project.getProvince());
            } else {
                staMap.put("location", "");
                Map<String, Object> locationMap = staService.getStaLocation(sta.getMac());
                if (locationMap != null) {
                    if (locationMap.get("province") != null) {
                        staMap.put("location", locationMap.get("province").toString());
                    }
                }
            }
            if (sta.getSource() == 0) {
                staMap.put("source", "wis");
            } else {
                staMap.put("source", "魔盒");
            }
            list.add(staMap);
        }
        map.put("data", list);

        return map;
    }

    @RequestMapping(value="/getStaCountList", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getStaCountList(HttpServletRequest request, HttpServletResponse response) {
        return staService.getStaCountList();
    }

    @RequestMapping(value="/getStaActiveCountList", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getStaActiveCountList(HttpServletRequest request, HttpServletResponse response) {
        return staService.getStaActiveCountList();
    }

    private String industryToShort(String industry) {
        if (industry.contains("高教")) {
            return "university";
        } else if (industry.contains("零售")) {
            return "sell";
        } else if (industry.contains("金融")) {
            return "finance";
        } else if (industry.contains("运营商")) {
            return "telcom";
        } else if (industry.contains("能源")) {
            return "energy";
        } else if (industry.contains("海外市场")) {
            return "oversea";
        } else if (industry.contains("服务业")) {
            return "service";
        } else if (industry.contains("普教")) {
            return "school";
        } else if (industry.contains("政府")) {
            return "government";
        } else if (industry.contains("医疗")) {
            return "hospital";
        } else if (industry.contains("制造业")) {
            return "manufacturing ";
        } else if (industry.contains("企业其他")) {
            return "enterprise";
        } else if (industry.contains("交通")) {
            return "traffic";
        } else if (industry.contains("互联网")) {
            return "internet";
        } else {
            return "others";
        }
    }

    @RequestMapping(value="/getStaIndustryList", method=RequestMethod.POST)
    @ResponseBody
    public Map<String, List<Object>> getStaIndustryList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, List<Object>> result = new HashMap<>();
        List<Map<String, Object>> list = staService.getStaIndustryList();
        String industry = null;
        List<Object> industryList = new ArrayList<>();
        List<Object> dateList = new ArrayList<>();
        boolean haveDate = false;
        for (Map<String, Object> map: list) {
            if (industry != null && !map.get("industry").toString().equals(industry)) {
                result.put(industryToShort(industry), industryList);
                if (!haveDate) {
                    result.put("date", dateList);
                    haveDate = true;
                }
                industryList = new ArrayList<>();
            }
            industry = map.get("industry").toString();
            industryList.add(map.get("count"));
            if (!haveDate) {
                dateList.add(map.get("date"));
            }
        }
        if (!haveDate) {
            result.put("date", dateList);
        }
        result.put(industryToShort(industry), industryList);

        return result;
    }

    @RequestMapping(value="/getStaCountProject", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getStaCountProject(HttpServletRequest request, HttpServletResponse response) {
        return staService.getStaCountProject();
    }

    @RequestMapping(value="/getStaCountIndustry", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getStaCountIndustry(HttpServletRequest request, HttpServletResponse response) {
        return staService.getStaCountIndustry();
    }

    @RequestMapping(value="/getStaCountProvince", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getStaCountProvince(HttpServletRequest request, HttpServletResponse response) {
        return staService.getStaCountProvince();
    }

    @RequestMapping(value="/getManufacturerStatistics", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getManufacturerStatistics(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> staManufacturerList = staService.getManufacturerCount();
        Integer totalCount = 0, topCount = 0;
        int i = 0;

        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> staManufacturer:staManufacturerList) {
            Map<String, Object> map = new HashMap<>();
            if (i < 8) {
                map.put("name", staManufacturer.get("manufacturer"));
                map.put("value", staManufacturer.get("count"));
                topCount += Integer.parseInt(staManufacturer.get("count").toString());
                result.add(map);
            }
            i++;
            totalCount += Integer.parseInt(staManufacturer.get("count").toString());
         }
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Others");
        map.put("value", totalCount - topCount);
        result.add(map);

        return result;
    }

    @RequestMapping(value="/getOsStatistics", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getOsStatistics(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> staManufacturerList = staService.getStaOsCount();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> staOsStatistics:staManufacturerList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", staOsStatistics.get("os"));
            map.put("value", staOsStatistics.get("count"));
            result.add(map);
        }
        return result;
    }

    @RequestMapping(value="/getBandStatistics", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getBandStatistics(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> sta5gStatisticsList = staService.getSta5gCount();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> sta5gStatistics:sta5gStatisticsList) {
            Map<String, Object> map = new HashMap<>();
            Integer is5g = Integer.parseInt(sta5gStatistics.get("is5g").toString());
            if (is5g == 0) {
                map.put("name", "--");
            } else if (is5g == 1) {
                map.put("name", "5g");
            } else {
                map.put("name", "2.4g");
            }
            map.put("value", sta5gStatistics.get("count"));
            result.add(map);
        }
        return result;
    }


    @RequestMapping(value="/getClientTypeStatistics", method=RequestMethod.POST)
    @ResponseBody
    public List<Map<String, Object>> getClientTypeStatistics(HttpServletRequest request, HttpServletResponse response) {
        List<Map<String, Object>> staClientTypeStatisticsList = staService.getStaClientTypeCount();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> staClientTypeStatistics:staClientTypeStatisticsList) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", staClientTypeStatistics.get("clientType"));
            map.put("value", staClientTypeStatistics.get("count"));
            result.add(map);
        }
        return result;
    }
}
