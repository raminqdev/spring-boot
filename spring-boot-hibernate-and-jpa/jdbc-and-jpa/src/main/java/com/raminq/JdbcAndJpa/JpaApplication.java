package com.raminq.JdbcAndJpa;

import com.raminq.JdbcAndJpa.entity.Person;
import com.raminq.JdbcAndJpa.jdbc.PersonJdbcDao;
import com.raminq.JdbcAndJpa.jdbc.PersonPureJdbcDao;
import com.raminq.JdbcAndJpa.jpa.PersonJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


@SpringBootApplication
public class JpaApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(JpaApplication.class, args);
    }

    private final PersonJpaRepository personJpaRepository;
    private Logger logger = LoggerFactory.getLogger(JpaApplication.class);

    @Autowired
    public JpaApplication(PersonJpaRepository personJpaRepository) {
        this.personJpaRepository = personJpaRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        logger.info("Jpa: Person find by id ->{}", personJpaRepository.findById(1001));

        Person newPerson = new Person("Ali", "Belgium", new Date());
        Person resultPerson = personJpaRepository.insertOrUpdate(newPerson);
        logger.info("Jpa: person inserted ->{}", resultPerson);

        resultPerson.setName("Ali ALex");
        resultPerson.setLocation("Cologne");
        resultPerson.setBirthDate(new GregorianCalendar(1990, Calendar.DECEMBER, 15).getTime());
        logger.info("Jpa: Number of rows updated ->{}", personJpaRepository.insertOrUpdate(resultPerson));

        personJpaRepository.deleteById(1);

        logger.info("Jpa: All persons ->{}", personJpaRepository.findAll());

    }
}
