package com.customer.jpa.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.QueryHint;

import java.util.Arrays;
import java.util.List;

import org.hibernate.ejb.QueryHints;
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
public class JPQLTest {
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

    @Test
    public void testJPQL() {
        String jpql = "from Customer c where c.age > ?";
        Query query = entityManager.createQuery(jpql);
        query.setParameter(1, 18);
        List<Customer> list = query.getResultList();
        for (Customer customer : list) {
            System.out.println("customer.getLastName() = " + customer.getLastName());
        }
    }

    @Test
    public void testJPQLPartly() {
        String jpql = "select new Customer(c.age, c.lastName) from Customer c where c.id > ?";
        List<Customer> list = entityManager.createQuery(jpql).setParameter(1, 2).getResultList();
        for (Customer customer : list) {
            System.out.println("customer.getAge() + \": \"+customer.getLastName() = " + customer.getAge() + ": " + customer.getLastName());
        }
    }

    /**
     * EntityManager.createNamedQuery 适用于在实体类上使用 @NamedQuery 标注的指定 name 的Query
     */
    @Test
    public void testNamedQuery() {
        Customer customer =
                entityManager.createNamedQuery("customerById", Customer.class).setParameter(1, 2).getSingleResult();
        System.out.println("customer = " + customer);
    }

    /**
     * EntityManager.createNativeQuery 方法适用于 使用本地 SQL 查询
     */
    @Test
    public void testNativeQuery() {
        Integer age =
                (Integer) entityManager.createNativeQuery("select age from tbl_customer where id = ?").setParameter(1
                        , 2).getSingleResult();
        System.out.println("age = " + age);
    }

    /**
     * JPA 开启查询缓存步骤:
     * 1. 在 persistence.xml 中开启查询缓存 <property name="hibernate.cache.use_query_cache" value="true"/>
     * 2. 调用 Query.setHint(QueryHints.HINT_CACHEABLE, true) 设置当前 Query 开启了查询缓存
     */
    @Test
    public void testQueryCaceh() {
        String jpql = "from Customer c where c.age > ?";
        // 给 Query 对象设置查询缓存
        Query query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
        query.setParameter(1, 18);
        List<Customer> list = query.getResultList();

        query = entityManager.createQuery(jpql).setHint(QueryHints.HINT_CACHEABLE, true);
        query.setParameter(1, 18);
        list = query.getResultList();
    }

    /**
     * 在 JPQL 中可以使用 ORDER BY 子句
     */
    @Test
    public void testJPQLOrderBy() {
        String jpql = "from Customer c where c.age > ? order by c.id";
        List<Customer> list = entityManager.createQuery(jpql)
                .setParameter(1, 1)
                .getResultList();
        System.out.println("list.size() = " + list.size());
    }

    /**
     * JPQL 中可以使用 GROUP BY 和 HAVING 子句
     */
    @Test
    public void testJPQLGroupByAndHaving() {
        String jpql = "select o.customer from Order o group by o.customer having count(o.id) >= ? ";
        List<Customer> list = entityManager.createQuery(jpql)
                .setParameter(1, 2L)
                .getResultList();
        System.out.println("list = " + list);
    }

    /**
     * 关联查询的时候 最后使用 LEFT OUTER JOIN FETCH
     * 一次性获取所有的数据
     */
    @Test
    public void testJPQLLeftOuterJoinFetch() {
        String jpql = "from Customer c left outer join fetch c.orders where c.id = ?";
        Customer customer = (Customer) entityManager.createQuery(jpql)
                .setParameter(1, 1)
                .getSingleResult();
        System.out.println("customer = " + customer);
        System.out.println("customer.getOrders() = " + customer.getOrders());
    }

    /**
     * JPQL 中可以使用子查询
     */
    @Test
    public void testJPQLSubQuery() {
        String jpql = "from Order o where o.customer = (select c from Customer c where c.lastName = ?)";
        List<Order> list = entityManager.createQuery(jpql)
                .setParameter(1, "Rock")
                .getResultList();
        System.out.println("list.size() = " + list.size());
    }

    /**
     * JPQL 中可以使用 JPQL 内置的一些函数
     */
    @Test
    public void testJPQLFunction() {
        String jpql = "select upper(c.email) from Customer c";
        List<String> list = entityManager.createQuery(jpql).getResultList();
        System.out.println("list = " + list);
    }

    /**
     * 可以使用 JPQL 进行 UPDATE/DELETE 操作
     */
    @Test
    public void testJPQLExecuteUpdate() {
        String jpq = "update Customer c set c.lastName = ?, c.email = ? where c.id = ?";
        entityManager.createQuery(jpq)
                .setParameter(1, "Bob")
                .setParameter(2, "bob@customer.com")
                .setParameter(3, 1)
                .executeUpdate();
    }
}
