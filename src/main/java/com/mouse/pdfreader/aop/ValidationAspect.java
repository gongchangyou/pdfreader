package com.mouse.pdfreader.aop;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.executable.ExecutableValidator;
import java.util.Set;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/13 21:43
 */
@Slf4j
@Component
@Aspect
@Order(10)
public class ValidationAspect {

    @Autowired
    @Qualifier("executableValidator")
    private ExecutableValidator validator;
//    @Around(value = "within(com.mouse.pdfreader.*) && execution(public * com.mouse.pdfreader.*.*(..)) && @annotation(com.mouse.pdfreader.aop.ParamValidation)")
    @Around(value = "@annotation(paramValidation) ")
    public Object process(ProceedingJoinPoint proceedingJoinPoint, ParamValidation paramValidation) {
        val sw= new StopWatch();
        sw.start("validation");
        Set<ConstraintViolation<Object>> constraintViolations = validator.validateParameters(proceedingJoinPoint.getTarget(), ((MethodSignature)proceedingJoinPoint.getSignature()).getMethod(), proceedingJoinPoint.getArgs());
        sw.stop();
        log.info(sw.prettyPrint());

        if(constraintViolations.size() > 0){
            final String constraintMessage = constraintViolations.stream()
                    .findFirst()
                    .map(ConstraintViolation::getMessage)
                    .orElse("");

            return constraintMessage;
        }

        try {
            return proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("failed", e);
            return "failed";
        }
    }
}
