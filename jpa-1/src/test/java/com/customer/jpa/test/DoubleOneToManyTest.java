package com.customer.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.jpa.entities.doubleone2many.Customer;
import com.customer.jpa.entities.doubleone2many.Order;


/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class DoubleOneToManyTest {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
    }

    @After
    public void destroy() {
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * 在双向一对多的关联关系中
     * 如果先插入多的一方的数据，再插入一的一方的数据，则会多 生成 4 条 UPDATE 语句
     * 如果先插入一的一方的数据，再插入多的一方的数据，则会多 生成 2 条 UPDATE 语句
     * 这是因为双方都维护了关联关系的原因
     * <p>
     * 建议使用多的一方来维护关联关系，可以通过 设置 @OneToMany.mappedBy=${多的一方中组合的一的一方的属性} 来设置
     * 注意，如果在一的一方使用了 @OneToMany.mappedBy 属性，则不能在使用 @JoinColumn 指定关联关系的外键
     * 这样在插入的时候，先插入一的一方，再插入多的一方，就不会生成多余的 UPDATE 语句
     */
    @Test
    public void testPersist() {

        Customer customer = new Customer("Jetty", "jetty@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order1 = new Order("O-Jetty-1");
        Order order2 = new Order("O-Jetty-2");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        entityManager.persist(customer);

        entityManager.persist(order1);
        entityManager.persist(order2);

    }

}
