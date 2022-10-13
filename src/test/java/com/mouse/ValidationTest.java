package com.mouse;

import com.mouse.pdfreader.Node;
import com.mouse.pdfreader.ParamData;
import com.mouse.pdfreader.TestService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gongchangyou
 * @version 1.0
 * @date 2022/10/6 15:24
 */
@Slf4j
@SpringBootTest(classes ={com.mouse.pdfreader.TestService.class, com.mouse.pdfreader.aop.ValidationAspect.class})
public class ValidationTest {
    @Autowired
    private TestService testService;
    @Test
    void test() {
        val result= testService.test(ParamData.builder()
                .age(3).name("asd").build());
        log.info("result={}", result);
    }
}
