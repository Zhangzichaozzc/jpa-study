package com.customer.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.jpa.entities.singleone2many.Customer;
import com.customer.jpa.entities.singleone2many.Order;


/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class OneToManyTest {
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
     * 在单向一对多的关系中，无论先插入多的一方还是先插入一的一方，都生成 UPDATE 语句
     */
    @Test
    public void testPersist() {

        Customer customer = new Customer("Rock", "rock@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Order order1 = new Order("O-Rock-1");
        Order order2 = new Order("O-Rock-2");

        customer.getOrders().add(order1);
        customer.getOrders().add(order2);

        entityManager.persist(customer);

        entityManager.persist(order1);
        entityManager.persist(order2);
    }

    /**
     * 单项一对多种，默认使用懒加载的加载方式，如果没有使用到 关联属性的信息，则不会加载 关联属性的信息
     * 可以通过设置 @OneToMany.fetch=FetchType.EAGER 来设置使用 LEFT OUTTER JOIN 的方式来立即加载关联关系的信息
     */
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println("order.getOrderName() = " + customer.getLastName());
        System.out.println(customer.getOrders().getClass());
        for (Order order : customer.getOrders()) {
            System.out.println(order.getOrderName());
        }
    }

    @Test
    public void testUpdate() {
        entityManager.find(Customer.class, 1).getOrders().iterator().next().setOrderName("0-ZZ-10");
    }

    /**
     * 单向一对多中再没有配置 cascade 属性的时候可以 删除一的一方的数据，是通过将一的一方在多的一方的的关联外键设置为 null， 然后再进行删除
     * 可以通过 设置 @OneToMany.cascade = CascadeType.REMOVE 来设置级联删除，这样在删除一的一方的数据时，会级联删除多的一方的关联数据
     */
    @Test
    public void testdelete() {

        entityManager.remove(entityManager.find(Customer.class, 2));
    }
}
