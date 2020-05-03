package com.customer.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.jpa.entities.doublemany2many.Catagory;
import com.customer.jpa.entities.doublemany2many.Item;


/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class DoubleManyToManyTest {
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
     * 在双向多对多的关联中，需要有一方不要维护关联关系, 通过 @ManyToMany.mappedBy 属性来设置
     * 中间表的维护需要在维护关联关系的一方进行维护，需要使用 @JoinTable 进行描述
     * 在查询中，无论先插入维护关联关系的一方还是先插入没有维护关联关系的一方，都会生成一样的 SQL
     */
    @Test
    public void testPersist() {

        Catagory catagory = new Catagory("C-CC");
        Catagory catagory2 = new Catagory("C-DD");

        Item item = new Item("I-CC");
        Item item2 = new Item("I-DD");

        catagory.getItems().add(item);
        catagory.getItems().add(item2);

        catagory2.getItems().add(item);
        catagory2.getItems().add(item2);

        item.getCatagorys().add(catagory);
        item.getCatagorys().add(catagory2);

        item2.getCatagorys().add(catagory);
        item2.getCatagorys().add(catagory2);

        entityManager.persist(item);
        entityManager.persist(item2);
        entityManager.persist(catagory);
        entityManager.persist(catagory2);
    }

    /**
     * 在多对多双向关联关系中，无论查询维护了关联关系的还是查询没有维护关联关系的一方
     * 都是进行懒加载
     */
    @Test
    public void testFind() {
        Catagory catagory = entityManager.find(Catagory.class, 1);
        System.out.println("catagory.getCataName() = " + catagory.getCataName());
        System.out.println("catagory.getItems().getClass().getName() = " + catagory.getItems().getClass().getName());
    }

    @Test
    public void testFind2() {
        Item item = entityManager.find(Item.class, 1);
        System.out.println("item.getItemName() = " + item.getItemName());
        System.out.println("item.getCatagorys().getClass().getName() = " + item.getCatagorys().getClass().getName());
    }

}
