package com.raminq.JdbcAndJpa.jdbc;

import com.raminq.JdbcAndJpa.entity.Person;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
   Here we use JDBC only
 */

@Repository
public class PersonPureJdbcDao {

    static final String DB_URL = "jdbc:h2:mem:testdb";
    static final String USER = "sa";
    static final String PASS = "";

    public List<Person> findAll() {
        List<Person> personList = new ArrayList<>();

        try (
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Person");
        ) {

            while (rs.next()) {
                Person person = new Person();

                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setLocation(rs.getString("location"));
                person.setBirthDate(rs.getDate("birth_date"));

                personList.add(person);
            }

            return personList;

        } catch (SQLException e) {
            e.printStackTrace();
            return personList;
        }
    }
}
