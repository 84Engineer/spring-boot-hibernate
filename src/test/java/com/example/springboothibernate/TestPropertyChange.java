package com.example.springboothibernate;

import com.example.springboothibernate.service.TestConcurrentAccessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPropertyChange {

    @Autowired
    private TestConcurrentAccessService service;

    @Test
    public void test() {
        Thread t = new Thread(() -> {
            try {
                service.changeBookName();
            } catch (Exception e) {
                //ignore
            }
        });
        t.start();
        service.printBookName();
    }
}
