package com.raminq.JdbcAndJpa.jdbc;

import com.raminq.JdbcAndJpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PersonJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonJdbcDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        return jdbcTemplate.query("SELECT * FROM Person",
                BeanPropertyRowMapper.newInstance(Person.class));
    }

    public Person findById(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=?",
                BeanPropertyRowMapper.newInstance(Person.class), id);
    }

    public List<Person> findByNameAndLocation(String name, String location) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE name=? AND location=?",
                BeanPropertyRowMapper.newInstance(Person.class), name, location);
    }

    public int deleteById(int id) {
        return jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public int insert(Person person) {
        return jdbcTemplate.update("INSERT INTO Person(id, name, location, birth_date) VALUES (?,?,?,?)",
                person.getId(),
                person.getName(),
                person.getLocation(),
                new Timestamp(person.getBirthDate().getTime()));
    }

}
