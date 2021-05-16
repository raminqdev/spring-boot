package com.raminq.JdbcAndJpa;

import com.raminq.JdbcAndJpa.entity.Person;
import com.raminq.JdbcAndJpa.jdbc.PersonJdbcDao;
import com.raminq.JdbcAndJpa.jdbc.PersonPureJdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class JdbcAndJpaApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(JdbcAndJpaApplication.class, args);
    }

    private final PersonJdbcDao personJdbcDao;
    private final PersonPureJdbcDao pureJdbcPersonDao;
    private Logger logger = LoggerFactory.getLogger(JdbcAndJpaApplication.class);

    @Autowired
    public JdbcAndJpaApplication(PersonJdbcDao personJdbcDao, PersonPureJdbcDao pureJdbcPersonDao) {
        this.personJdbcDao = personJdbcDao;
        this.pureJdbcPersonDao = pureJdbcPersonDao;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("All persons ->{}", personJdbcDao.findAll());

        logger.info("All persons Pure JDBC->{}", pureJdbcPersonDao.findAll());

        logger.info("Person find by id ->{}", personJdbcDao.findById(1001));
        logger.info("Person find by name and location ->{}", personJdbcDao.findByNameAndLocation("ramin", "tehran"));

        logger.info("Number of rows deleted ->{}", personJdbcDao.deleteById(1001));

        Person newPerson = new Person();
        newPerson.setId(1004);
        newPerson.setName("Ali");
        newPerson.setLocation("Belgium");
        newPerson.setBirthDate(new Date());
        logger.info("Number of rows inserted ->{}", personJdbcDao.insert(newPerson));
    }
}
