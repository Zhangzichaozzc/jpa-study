package com.customer.jpa.entities.doubleone2one;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Department
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Table(name = "tbl_dept")
@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "dept_name")
    private String deptName;

    // 在基于外键的一对一关联关系中， 维护外键的一方需要将 该外键列添加为 唯一键
    @JoinColumn(name = "mgr_id", unique = true)
    // 使用 @OneToOne 来映射 一对一 关联关系
    @OneToOne(fetch = FetchType.LAZY)
    private Manager mgr;

    public Department() {
    }

    public Department(String deptName) {
        this.deptName = deptName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Manager getMgr() {
        return mgr;
    }

    public void setMgr(Manager mgr) {
        this.mgr = mgr;
    }
}
