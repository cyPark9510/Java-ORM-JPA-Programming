package ex06_advenced_mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex06Team extends Ex06BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")   // MappedBy 속성의 값은
                                    // 연관관계의 주인인 Member.team
    private List<Ex06Member> members = new ArrayList<>();

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

    public List<Ex06Member> getMembers() {
        return members;
    }

    // 연관관계 편의 메소드
    public void addMember(Ex06Member member) {
        members.add(member);
    }
}
