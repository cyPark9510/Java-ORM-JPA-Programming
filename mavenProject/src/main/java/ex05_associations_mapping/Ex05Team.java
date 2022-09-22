package ex05_associations_mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex05Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "MEMBER_ID")
    private List<Ex05Member> members = new ArrayList<>();

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

    public List<Ex05Member> getMembers() {
        return members;
    }

    // 연관관계 편의 메소드
    public void addMember(Ex05Member member) {
        members.add(member);
    }
}
