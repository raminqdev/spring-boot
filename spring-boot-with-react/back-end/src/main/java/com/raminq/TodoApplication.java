package com.raminq;

import com.raminq.webservice.todo.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;

@SpringBootApplication
public class TodoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TodoApplication.class, args);
    }

    final EntityManager entityManager;

    public TodoApplication(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Todo todo = new Todo(10001L, "test", "Learn JPA", new Date(), false);
        entityManager.merge(todo);
        Todo todo2 = new Todo(10001L, "test", "Learn Statics", new Date(), false);
        entityManager.merge(todo);
        Todo todo3 = new Todo(10001L, "test", "Learn Hibernate", new Date(), false);
        entityManager.merge(todo);

    }
}
