package com.fanshang.noveltoutiao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/visit")
public class VisitPageContrller {

    @RequestMapping("/novelpage1")
    public Object novelPage1(){
        return "novelpage1";
    }

    @RequestMapping("/novelpage2")
    public Object novelPage2(){
        return "novelpage2";
    }

}
