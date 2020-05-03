package com.customer.spring.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customer.spring.jpa.dao.PersonDao;
import com.customer.spring.jpa.entity.Person;

/**
 * PersonService
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Service
public class PersonService {

    @Autowired
    private PersonDao personDao;

    /**
     * 使用声明式事务
     *
     * @param person1
     */
    @Transactional
    public void save(Person person1, Person person2) {
        personDao.save(person1);
        int i = 1 / 0;
        personDao.save(person2);
    }

}
