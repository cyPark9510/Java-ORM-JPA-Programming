package ex04_associations;

import javax.persistence.*;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Ex04Member> members = new ArrayList<>();

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

    public List<Ex04Member> getMembers() {
        return members;
    }

    // 연관관계 편의 메소드
    public void addMember(Ex04Member member) {
        members.add(member);

        member.setTeam(this);
    }
}
