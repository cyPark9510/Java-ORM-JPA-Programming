package ex01_start;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity  // JPA가 로딩될 때, 관리대상으로 인식
//@Table(name = "user")
public class Ex01Member {

    @Id  // DB PK와 매핑
    private Long id;

    private String name;

    public Ex01Member() {}

    public Ex01Member(Long id, String name) {
        this.id = id;
        this.name = name;
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
}