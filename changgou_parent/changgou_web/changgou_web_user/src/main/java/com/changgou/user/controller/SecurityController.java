package com.changgou.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Controller
@RequestMapping("/userweb1")
public class SecurityController {

    @RequestMapping("/forget")
    public String forget() {
        return "forget";
    }
    @RequestMapping("/change")
    public String change() {
        return "change";
    }
    @RequestMapping("/success")
    public String success() {
        return "resetSuccess";
    }

    @RequestMapping("/reset")
    public String reset() {
        return "home-setting-safe";
    }




}
