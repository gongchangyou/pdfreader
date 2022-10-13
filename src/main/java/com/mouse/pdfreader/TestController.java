package com.mouse.pdfreader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/13 22:19
 */
@RestController
@RequestMapping(value = "test")
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping("/")
    String test(@RequestParam(name = "name", required = false) String name
            ,@RequestParam(name = "age", required = false) int age  ){
        return testService.test(ParamData.builder()
                .name(name)
                .age(age)
                .build());
    }
}
