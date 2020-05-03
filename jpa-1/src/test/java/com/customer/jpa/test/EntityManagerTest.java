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

/**
 * EntityManagerTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class EntityManagerTest {
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
     * EntityManager.persist() 方法与 Hibernate 中 Session.persist() 方法的使用方式相同，
     * 与 Session.save() 方法的不同是，当 EntityManager.persist() 方法的参数有 OID 时，则插入失败
     */
    @Test
    public void testPersist() {
        Customer bob = new Customer("Bob", "bob@customer.com", 20);
//        bob.setId(10);
        bob.setBirth(new Date());
        bob.setCreateTime(new Date());
        entityManager.persist(bob);
        System.out.println("---------------------------");
    }

    /**
     * EntityManager.find() 方与 Session.get() 方相同，都是返回的是实体类本身的对象，不是返回代理对象
     */
    @Test
    public void testFind() {
        Customer customer = entityManager.find(Customer.class, 1);
        System.out.println("-------------------");
        System.out.println("customer = " + customer);
    }

    /**
     * EntityManager.getReference() 方法与 Session.load() 方法相同
     * 1. 返回的是实体类的代理对象
     * 2. 拉加载，该方法调用后返回的对象只有 OID 被初始化，其他的字段都没有被初始化，只有在使用到其他属性的时候才会生成 SQL ，初始化其他属性
     * 3. 如果在代理对象没有被初始化时，关闭了 EntityManager， 则会发生懒加载异常
     */
    @Test
    public void testGetReference() {
        Customer customer = entityManager.getReference(Customer.class, 1);
        System.out.println("customer.getClass().getName() = " + customer.getClass().getName());
        System.out.println("---------------------");
        System.out.println("customer = " + customer);
    }

    /**
     * EntityManager.remove 与 Session.delete() 方法的异同
     * 相同:
     * 两个方法都可以删除持久化对象
     * 不同:
     * EntityManager.remove 不能删除临时状态的对象
     * Session.delete 可以删除临时状态的对象
     */
    @Test
    public void testRemove() {
        Customer customer = entityManager.find(Customer.class, 1);
        customer.setId(1);
        entityManager.remove(customer);
    }

    /**
     * 如果要进行 merge 操作的对象是一个临时状态的对象
     * 1. JPA 会创建一个跟该临时状态对象类型一样的对象，并且将临时对象的属性都复制到 JPA 创建的对象中
     * 2. JPA 将自己创建的对象进行持久化并返回该持久化对象的引用
     */
    @Test
    public void testMerge1() {
        Customer customer = new Customer("Bob", "bob@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());

        Customer mergedCustomer = entityManager.merge(customer);
        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("mergedCustomer.getId() = " + mergedCustomer.getId());
        System.out.println("customer == mergedCustomer = " + (customer == mergedCustomer));
    }

    /**
     * 如果meger操作的对象是一个游离状态的对象
     * 1. JPA 会在缓存中查找有没有该对象的缓存信息,如果缓存中没有该游离状态对象的缓存信息
     * 2. 数据表中也没有该游离状态对象的信息
     * 3. JPA 会创建一个跟该游离状态对象类型一样的对象，并且将游离对象的属性都复制到 JPA 创建的对象中
     * 4. JPA 将自己创建的对象进行持久化并返回该持久化对象的引用
     */
    @Test
    public void testMeger2() {
        Customer customer = new Customer("Jary", "jary@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(100);

        Customer mergedCustomer = entityManager.merge(customer);
        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("mergedCustomer.getId() = " + mergedCustomer.getId());
        System.out.println("customer == mergedCustomer = " + (customer == mergedCustomer));
    }

    /**
     * 如果merge 操作对象是游离状态的
     * 1. JPA 会在缓存中查找有没有该对象的缓存信息,如果缓存中没有该游离状态对象的缓存信息
     * 2. 数据库中有该游离状态的对象的信息
     * 3. JPA 会从数据表中获取 OID 与游离状态相同的持久化状态数据，并存放在缓存中，并且将游离对象的属性都复制到 JPA 获取的持久化状态对象中
     * 4. 如果 游离状态数据域缓存中的数据不相同，则 JPA 会向数据库发送 UPDATE 语句
     */
    @Test
    public void testMerge3() {
        Customer customer = new Customer("Clerk", "clerk@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(3);

        Customer mergedCustomer = entityManager.merge(customer);
        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("mergedCustomer.getId() = " + mergedCustomer.getId());
        System.out.println("customer == mergedCustomer = " + (customer == mergedCustomer));
    }

    /**
     * 如果merge 操作对象是游离状态的
     * 1. JPA 会在缓存中查找有没有该对象的缓存信息,并且缓存中存在 OID 与游离状态对象相同的对象
     * 3. JPA 会比较缓存中 OID 与游离状态相同的持久化状态数据，如果数据不相同，则将游离对象的属性都复制到 JPA 获取的持久化状态对象中
     * 4. 如果 游离状态数据域缓存中的数据不相同，则 JPA 会向数据库发送 UPDATE 语句
     */
    @Test
    public void testMerge4() {
        Customer customer = new Customer("rock", "Rock@customer.com", 20);
        customer.setBirth(new Date());
        customer.setCreateTime(new Date());
        customer.setId(3);

        entityManager.find(Customer.class, 4);
        Customer mergedCustomer = entityManager.merge(customer);
        System.out.println("customer.getId() = " + customer.getId());
        System.out.println("mergedCustomer.getId() = " + mergedCustomer.getId());
        System.out.println("customer == mergedCustomer = " + (customer == mergedCustomer));
    }

    @Test
    public void testFlush() {
        Customer customer = entityManager.find(Customer.class, 3);
        customer.setEmail("rock@customer.com");
        customer.setLastName("Rock");

        entityManager.flush();
    }

    @Test
    public void testRefresh() {
        Customer customer = entityManager.find(Customer.class, 3);
        entityManager.refresh(customer);
        Customer customer2 = entityManager.find(Customer.class, 3);
    }

}
