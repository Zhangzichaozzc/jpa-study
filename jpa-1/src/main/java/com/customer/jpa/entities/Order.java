package com.customer.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Order
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Table(name = "tbl_order")
@Entity(name = "Order_many2one")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "order_name")
    private String orderName;
    // @JoinColume 的 name 属性指定多放的外键列名称
    @JoinColumn(name = "customer_id")
    // 使用 @ManyToOne 指定表的关联关系
    // 通过 @ManyToOne.fetch 属性可以设置在多对一查询时使用懒加载的方式
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    public Order(String orderName) {
        this.orderName = orderName;
    }

    public Order() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
