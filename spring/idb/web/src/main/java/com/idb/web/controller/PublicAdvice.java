package com.idb.web.controller;

import com.idb.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class PublicAdvice {
    @Autowired
    protected MenuService menuService;

    @ModelAttribute
    public void  addCommonModel(Model model, HttpServletRequest request) {
        model.addAttribute("navs", menuService.getNavMenus());
    }
}
