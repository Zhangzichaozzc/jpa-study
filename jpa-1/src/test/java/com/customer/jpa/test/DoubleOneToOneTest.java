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
import com.customer.jpa.entities.doubleone2one.Department;
import com.customer.jpa.entities.doubleone2one.Manager;


/**
 * ManyToOneTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
public class DoubleOneToOneTest {
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
     *
     */
    @Test
    public void testPersist() {

        Manager manager = new Manager("M-AA");
        Department department = new Department("D-AA");

        manager.setDept(department);
        department.setMgr(manager);

        entityManager.persist(manager);
        entityManager.persist(department);
    }

    /**
     * 在 @OneToOne 注解中, fetch 属性的默认值为 FetchType.EAGER, 会立即加载，
     * 在维护啊外键关系的一方，可以通过 设置 @OneToOne.fetch=FetchType.LAZY 来进行懒加载
     */
    @Test
    public void testFind() {
        Department department = entityManager.find(Department.class, 1);
        System.out.println(department.getDeptName());
        System.out.println(department.getMgr().getClass().getName());
//        System.out.println("department.getMgr().getMgrName() = " + department.getMgr().getMgrName());
    }

    /**
     * 在不维护啊外键关系的一方，默认也是立即加载，只是发送一条 LEFT OUTER JOIN 语句来获取到 关联属性的信息
     * 如果设置为 延迟加载则会生成两条单独的语句查询
     * 所以建议在不维护外键关系的一方，不要修改 fetch 属性的默认值
     */
    @Test
    public void testFind2() {
        Manager manager = entityManager.find(Manager.class, 1);
        System.out.println("manager.getMgrName() = " + manager.getMgrName());
        System.out.println("manager.getDept().getClass().getName() = " + manager.getDept().getClass().getName());
    }

}
