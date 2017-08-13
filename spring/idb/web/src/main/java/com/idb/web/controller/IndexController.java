package com.idb.web.controller;

import com.idb.service.StaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.text.DecimalFormat;

@Controller
public class IndexController {
    @Resource
    private StaService staService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String index(Model model) {
        logger.error("fuck");
        DecimalFormat df = new DecimalFormat("#,###");
        Integer staNum = staService.getStaCount("");
        model.addAttribute("staNum", df.format(staNum));
        return "index";
    }
}
