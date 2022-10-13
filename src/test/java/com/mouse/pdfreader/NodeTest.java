package com.mouse.pdfreader;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
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
public class NodeTest {
    @Test
    void test() {
        val node = Node.builder()
                .age(10)
                .name("name")
                .build();
        log.info("node = {}", node);
    }

    static Integer result = 0;
    @Test
    void multi() {
        val map = new ConcurrentHashMap<String, Integer>();
//        map.put("a", true);
        map.put("b", 356);
        val i = map.get("a");
        val j = map.get("a");
        val pool = new ThreadPoolExecutor(10,10, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());
        var r = new AtomicInteger();
//        val result = new Integer(100);
        val list = new LinkedBlockingQueue<>();
        for (int k=0;k<1000;k++) {
            pool.execute(() ->{
                val b = map.putIfAbsent("a", 800);
                log.info("b={}", b);
                synchronized (this) {
                    result += 1;
                    //                r.addAndGet(1);
                    list.add(result);
                }
            });
        }
        synchronized (i) {
            System.out.println("log");
        }
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("result={} list={}", result ,list.size());

    }
}
