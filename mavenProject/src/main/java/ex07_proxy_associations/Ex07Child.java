package ex07_proxy_associations;

import javax.persistence.*;

@Entity
public class Ex07Child {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Ex07Parent parent;

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

    public Ex07Parent getParent() {
        return parent;
    }

    public void setParent(Ex07Parent parent) {
        this.parent = parent;
    }
}
