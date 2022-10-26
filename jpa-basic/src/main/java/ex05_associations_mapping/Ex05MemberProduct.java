package ex05_associations_mapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ex05MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Ex05Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Ex05Product product;

    private int count;

    private int price;

    private LocalDateTime orderDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ex05Member getMember() {
        return member;
    }

    public void setMember(Ex05Member member) {
        this.member = member;
    }

    public Ex05Product getProduct() {
        return product;
    }

    public void setProduct(Ex05Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    public void setOrderDateTime(LocalDateTime orderDateTime) {
        this.orderDateTime = orderDateTime;
    }
}
