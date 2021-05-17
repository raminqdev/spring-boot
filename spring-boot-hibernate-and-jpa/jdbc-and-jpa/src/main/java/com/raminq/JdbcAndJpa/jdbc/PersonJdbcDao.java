package com.raminq.JdbcAndJpa.jdbc;

import com.raminq.JdbcAndJpa.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/*
   Here we use JDBC Template
 */
@Repository
public class PersonJdbcDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonJdbcDao(JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> findAll() {
        BeanPropertyRowMapper<Person> rowMapper = BeanPropertyRowMapper.newInstance(Person.class);
        return jdbcTemplate.query("SELECT * FROM Person",
                rowMapper);
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

    public int update(Person person) {
        return jdbcTemplate.update("UPDATE Person SET name=?, location=?, birth_date=? WHERE id=?",
                person.getName(),
                person.getLocation(),
                new Timestamp(person.getBirthDate().getTime()),
                person.getId());
    }

    //----- create out own RowMapper
    class PersonRowMapper implements RowMapper<Person> {
        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {

            Person person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setLocation(resultSet.getString("location"));
            person.setBirthDate(resultSet.getTimestamp("birth_date"));

            return person;
        }
    }

    public List<Person> findAllWithRowMapper() {
        BeanPropertyRowMapper<Person> rowMapper = BeanPropertyRowMapper.newInstance(Person.class);
        return jdbcTemplate.query("SELECT * FROM Person",
                new PersonRowMapper());
    }

}
