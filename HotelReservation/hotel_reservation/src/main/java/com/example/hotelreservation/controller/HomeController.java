package com.example.hotelreservation.controller;

import com.example.hotelreservation.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private SMSService smsService;

    @GetMapping("/")
    public String get_home() {
        return "/home";
    }
}