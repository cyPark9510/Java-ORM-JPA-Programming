package jpabook.jpashop.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Item {

    @Id
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;        // 이름

    private int price;          // 가격

    private int stackQuantity;  // 재고수량

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
}
