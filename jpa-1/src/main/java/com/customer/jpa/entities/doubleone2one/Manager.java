package com.customer.jpa.entities.doubleone2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Manager
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Entity
@Table(name = "tbl_mgr")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "mgr_name")
    private String mgrName;

    // 使用 @OneToOne 来描述 一对一关联关系
    // 在基于外键的 一对一 关联关系中， 在不维护外键的一方，建议不要维护关联关系，可以通过 @OneToOne.mappedBy 来设置维护关联关系一方的属性
    // 建议在不维护一对一外键关系的一方，建议不要修改 fetch 属性的值， 否则会多发 SQL 语句
    @OneToOne(mappedBy = "mgr"/*, fetch = FetchType.LAZY*/)
    private Department dept;

    public Manager() {
    }

    public Manager(String mgrName) {
        this.mgrName = mgrName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMgrName() {
        return mgrName;
    }

    public void setMgrName(String mgrName) {
        this.mgrName = mgrName;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }
}
