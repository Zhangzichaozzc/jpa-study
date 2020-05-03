package com.customer.jpa.entities.doublemany2many;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

/**
 * Catagory
 *
 * @author Zichao Zhang
 * @date 2020/5/3
 */
@Table(name = "tbl_cata")
@Entity
public class Catagory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "cata_name")
    private String cataName;

    @ManyToMany(mappedBy = "catagorys")
    private Set<Item> items = new HashSet<>();

    public Catagory(String cataName) {
        this.cataName = cataName;
    }

    public Catagory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCataName() {
        return cataName;
    }

    public void setCataName(String cataName) {
        this.cataName = cataName;
    }

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }
}
