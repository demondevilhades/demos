package com.hades.spring_simple;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/test")
public class TestController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    static {
        System.out.println("test static");
    }

    @PostConstruct
    public void init() {
        System.out.println("test init");
    }

    @RequestMapping("/test0")
    public void test() {
        System.out.println("test0");
    }
}
