package com.mouse.pdfreader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

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
@SpringBootTest
public class ReflectTest {
    @Test
    void test() {
        val a = Node.builder()
                .name("a")
                .age(10)
                .node(SubNode.builder()
                        .name("sub_a")
                        .age(5)
                        .build())
                .build();

        val b = Node.builder()
                .name("a")
                .age(10)
                .node(SubNode.builder()
                        .name("sub_a")
                        .age(6)
                        .build())
                .build();

        val sw= new StopWatch();
        sw.start();
        for(int i = 0; i <10000; i++) {
            for(val f : a.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                try {
                    f.get(a).equals(f.get(b));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
                //            log.info("result={}", isEqual(a, b));
        }
        sw.stop();
        log.info(sw.prettyPrint());
    }

    private boolean isEqual(Object a, Object b){
        if (a.getClass().equals(String.class) || a.getClass().equals(Integer.class)){
            return a.equals(b);
        } else {
            for(val f : a.getClass().getDeclaredFields()) {

                f.setAccessible(true);
                try {
                    if (!isEqual(f.get(a), f.get(b))){
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
    }



}
