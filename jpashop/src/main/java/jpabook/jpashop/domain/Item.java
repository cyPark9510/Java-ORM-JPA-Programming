package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {

    @Id
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;        // 이름

    private int price;          // 가격

    private int stackQuantity;  // 재고수량

    @ManyToMany(mappedBy = "items")
    private List<Category> categorys = new ArrayList<>();


    //==Getter, Setter==//
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStackQuantity() {
        return stackQuantity;
    }

    public void setStackQuantity(int stackQuantity) {
        this.stackQuantity = stackQuantity;
    }

    public List<Category> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<Category> categorys) {
        this.categorys = categorys;
    }
}
