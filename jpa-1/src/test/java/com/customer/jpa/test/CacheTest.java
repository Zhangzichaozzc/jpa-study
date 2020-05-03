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
import com.customer.jpa.entities.singleone2many.Order;


/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class CacheTest {
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
     * JPA 开启二级缓存的步骤：
     * 1. 引入产品支持的 二级缓存的插件的依赖 hibernate-ehcache
     * 2. 在类路径中添加 ehcache.xml 配置文件
     * 3. 在 persistence.xml 中添加 hibernate 开启二级缓存的配置
     * 4. 在 persistence.xml 中设置 shared-cache-mode 节点
     * 5. 在要启动二级缓存的实体类上添加 @Cacheable 注解
     */
    @Test
    public void testCache() {
        Customer customer = entityManager.find(Customer.class, 1);
        transaction.commit();
        entityManager.close();

        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();
        customer = entityManager.find(Customer.class, 1);

    }
}
