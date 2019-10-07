package com.example.springboothibernate.service;

import com.example.springboothibernate.entities.Book;
import com.example.springboothibernate.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class TestConcurrentAccessService {

    @Autowired
    private BookRepository bookRepository;
    private Book entity;

    public void printBookName() {
        load();
        int interval = 500;
        for (int i = 5000; i > 0; i -= interval) {
            System.out.println(entity.getName());
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this) {
            load();
            System.out.println(entity.getName());
        }
    }

    @Transactional
    public synchronized void changeBookName() {
        load();
        int interval = 1000;
        for (int i = 3000; i > 0; i -= interval) {
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            entity.setName(UUID.randomUUID().toString());
        }
        throw new RuntimeException("Rollback");
    }

    @Transactional
    public void load() {
        entity = bookRepository.findById(1L).get();
    }

}
