package com.mouse.pdfreader.config;

import lombok.val;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.executable.ExecutableValidator;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/13 22:23
 */
@Configuration
public class ValidatorConfiguration {
    @Bean(name = "executableValidator")
    ExecutableValidator get() {
        val validatorFactory = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory();
        return validatorFactory.getValidator().forExecutables();
    }
}
