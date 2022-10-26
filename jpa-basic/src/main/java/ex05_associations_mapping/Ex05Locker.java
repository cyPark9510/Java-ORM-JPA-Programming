package ex05_associations_mapping;

import javax.persistence.*;

@Entity
public class Ex05Locker {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToOne(mappedBy = "locker")
    private Ex05Member member;
}
