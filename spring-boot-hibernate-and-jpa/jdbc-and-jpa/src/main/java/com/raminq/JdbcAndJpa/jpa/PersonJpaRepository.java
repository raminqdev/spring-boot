package com.raminq.JdbcAndJpa.jpa;

import com.raminq.JdbcAndJpa.entity.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


@Repository
@Transactional
public class PersonJpaRepository {

    //connect to the database
    @PersistenceContext
    EntityManager entityManager;

    //we need to write HQL / JPQL
    public List<Person> findAll() {
        TypedQuery<Person> namedQuery = entityManager.createNamedQuery("find_all_person", Person.class);
        return namedQuery.getResultList();
    }

    public Person findById(int id) {
        return entityManager.find(Person.class, id);
    }

    public Person insertOrUpdate(Person person) {
        return entityManager.merge(person);
    }

    public void deleteById(int id) {
        Person result = findById(id);
        entityManager.remove(result);
    }

}
