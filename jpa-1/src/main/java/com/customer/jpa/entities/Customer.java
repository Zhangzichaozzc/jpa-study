package com.customer.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;

/**
 * Customer
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Table(name = "tbl_customer")
@Entity(name = "Customer_many2one")
public class Customer {
    //    @TableGenerator(name = "table_generator",
//            table = "jpa_id_generator",
//            pkColumnName = "pk_name",
//            pkColumnValue = "student_id",
//            valueColumnName = "pk_value",
//            allocationSize = 100)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "table_generator")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private int age;

    @Temporal(TemporalType.DATE)
    private Date birth;

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", birth=" + birth +
                ", createTime=" + createTime +
                '}';
    }

    public Customer(String lastName, String email, int age) {
        this.lastName = lastName;
        this.email = email;
        this.age = age;
    }

    public Customer() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
