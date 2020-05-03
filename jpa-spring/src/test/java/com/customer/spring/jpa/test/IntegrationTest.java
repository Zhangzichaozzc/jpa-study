package com.customer.spring.jpa.test;

import javax.sql.DataSource;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.customer.spring.jpa.PersonService;
import com.customer.spring.jpa.entity.Person;

/**
 * IntegrationTest
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class IntegrationTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSource() throws SQLException {
        System.out.println("dataSource = " + dataSource);
        System.out.println("dataSource.getConnection() = " + dataSource.getConnection());
    }

    @Test
    public void testSave() {
        Person person = new Person("Javascript", "javascript@customer.com", 30);
        Person person2 = new Person("C++", "c++@customer.com", 30);
        personService.save(person, person2);
    }

    @Autowired
    private PersonService personService;

}
