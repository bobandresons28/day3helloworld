package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureHelloController {

    @RequestMapping(value = "/secure-hello", method = RequestMethod.GET)
    @ResponseBody
    public String sayHello() {
        return "Hello, secure world!!";
    }

}