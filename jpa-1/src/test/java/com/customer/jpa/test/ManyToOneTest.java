package com.customer.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.jpa.entities.Customer;
import com.customer.jpa.entities.Order;

/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class ManyToOneTest {
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
     * 多对一关联插入多表的时候，与Hibernate 一样
     * 建议:
     * 先插入 一的一方的数据
     * 然后再插入 多的一方的数据
     * 否则会多生成不必要的 UPDATE 语句，影响性能
     */
    @Test
    public void testPersist() {

        Customer customer = new Customer("Jeck", "jeck@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order1 = new Order("O-Jeck-1");
        Order order2 = new Order("O-Jeck-2");

        order1.setCustomer(customer);
        order2.setCustomer(customer);

        entityManager.persist(order1);
        entityManager.persist(order2);

        entityManager.persist(customer);
    }

    /**
     * find 方法默认使用 left join 的方式将 Order 的数据一次性取出
     * 也可以 通过 @ManyToOne.fetch 属性可以设置在多对一查询时使用懒加载的方式
     */
    @Test
    public void testFind() {
        Order order = entityManager.find(Order.class, 1);
        System.out.println("order.getOrderName() = " + order.getOrderName());

        System.out.println("order.getCustomer().getLastName() = " + order.getCustomer().getLastName());
    }

    @Test
    public void testUpdate() {
        Order order = entityManager.find(Order.class, 5);
        order.getCustomer().setLastName("Wow");
        order.getCustomer().setEmail("wow@customer.coom");
    }

    /**
     * 与 Hibernate 的 Session.delete 相似
     * 可以删除多方的数据
     * 不可以删除一方的数据
     */
    @Test
    public void testdelete() {
        entityManager.remove(entityManager.find(Order.class, 1));
    }
}
