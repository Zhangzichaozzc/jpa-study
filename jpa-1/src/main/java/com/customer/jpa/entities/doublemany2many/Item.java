package com.customer.jpa.entities.doublemany2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Item
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Entity
@Table(name = "tbl_item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_name")
    private String itemName;

    // 使用 @JoinTable 来指定维护关联关系的第三张表的信息
    // 1. name: 指定第三张表的 表名
    // 2. joinColumns: 指定本表在第三方表中的外键依赖
    // 2.1 @JoinColumn.name: 指定本表在第三张表中外键的列名
    // 2.2 @JoinColumn.referencedColumnName: 指定本表在第三张表中的外键依赖的本表的主键
    // 3. inverseJoinColumns: 指定与本表进行多对多映射的表在 第三张表中的外键依赖
    // 3.1 @JoinColumn.name: 指定 与本表进行多对多映射的表 在第三张表中外键的列名
    // 3.2 @JoinColumn.referencedColumnName: 指定 与本表进行多对多映射的表 在第三张表中的外键依赖的本表的主键
    @JoinTable(
            name = "tbl_item_cata",
            joinColumns = @JoinColumn(name = "i_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "c_id", referencedColumnName = "id")
    )
    // 使用 @ManyToMany 来指定多对多关联关系, 如果没有指定 mappedBy 属性，则是由本方来维护关联关系
    @ManyToMany
    private Set<Catagory> catagorys = new HashSet<>();

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Set<Catagory> getCatagorys() {
        return catagorys;
    }

    public void setCatagorys(Set<Catagory> catagorys) {
        this.catagorys = catagorys;
    }
}
