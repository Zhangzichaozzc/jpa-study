package com.customer.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Date;

import org.hibernate.Session;

import com.customer.jpa.entities.Customer;

/**
 * JpaHelloWorld
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class JpaHelloWorld {
    public static void main(String[] args) {
        // 创建EntityManagerFactory
        String persistenceUnitName = "jpa-1";
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);

        // 创建 EntityManager
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // 开启事务
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // 执行持久化操作
        Customer customer = new Customer("Java", "java@customer.com", 30);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        entityManager.persist(customer);
        // 提交事务
        transaction.commit();

        // 关闭 EntityManager
        entityManager.close();

        // 关闭 EntityManagerFactory
        entityManagerFactory.close();

    }
}
