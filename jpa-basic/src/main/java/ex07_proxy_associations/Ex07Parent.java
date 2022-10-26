package ex07_proxy_associations;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex07Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ex07Child> childList = new ArrayList<>();

    public void addChild(Ex07Child child) {
        childList.add(child);
        child.setParent(this);
    }

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

    public List<Ex07Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Ex07Child> childList) {
        this.childList = childList;
    }
}
