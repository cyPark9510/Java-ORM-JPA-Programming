package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

//    @NotEmpty -> saveMemberV1
    private String name;

    @Embedded
    private Address address;

//    @JsonIgnore -> membersV1
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
}
