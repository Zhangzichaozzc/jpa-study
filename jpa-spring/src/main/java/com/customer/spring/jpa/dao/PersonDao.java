package com.customer.spring.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.customer.spring.jpa.entity.Person;

/**
 * PersonDao
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Repository
public class PersonDao {

    /**
     * 将 EntityManager 与线程绑定需要使用 @PersistenceContext 注解来注解 EntityManager 主键
     */
    @PersistenceContext
    private EntityManager entityManager;

    public void save(Person person) {
        entityManager.persist(person);
    }

}
