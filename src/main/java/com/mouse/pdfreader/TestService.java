package com.mouse.pdfreader;

import com.mouse.pdfreader.aop.ParamValidation;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/13 21:40
 */
@Service
public class TestService {

    @ParamValidation
    public String test(@Valid ParamData param){
        return "success";
    }
}
