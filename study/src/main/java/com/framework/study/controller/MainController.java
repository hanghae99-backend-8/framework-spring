package com.framework.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class MainController {

     @GetMapping("/")
     public String redirect() {
         return "redirect:/swagger-ui/index.html";
     }

     @GetMapping("health")
     public String healthCheck() {
         return "OK";
     }
     
}
