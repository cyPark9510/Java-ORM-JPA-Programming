package ex05_associations_mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex05Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "product")
    private List<Ex05MemberProduct> memberProducts = new ArrayList<>();
}
