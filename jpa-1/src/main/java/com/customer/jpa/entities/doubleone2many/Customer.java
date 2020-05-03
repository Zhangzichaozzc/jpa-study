package com.customer.jpa.entities.doubleone2many;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Customer
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@NamedQuery(name = "customerById", query = "from Customer c where c.id = ?")
@Table(name = "tbl_customer")
@Entity
@Cacheable
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private int age;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    // 使用 @JoinColumn.name 指定关联关系在 n 方的外键列
    // 如果使用了 @OneToMany.mappedBy 则不能在使用 @JoinColumn 注解
    //    @JoinColumn(name = "customer_id")
    // 使用 @OneToMany 在一的一方进行关联关系映射
    // 可以通过设置 @OneToMany.fetch=FetchType.EAGER 来设置使用 LEFT OUTTER JOIN 的方式来立即加载关联关系的信息
    @OneToMany(/*fetch = FetchType.EAGER,*/ cascade = CascadeType.REMOVE, mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();

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

    public Customer(int age, String lastName) {
        this.lastName = lastName;
        this.age = age;
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
