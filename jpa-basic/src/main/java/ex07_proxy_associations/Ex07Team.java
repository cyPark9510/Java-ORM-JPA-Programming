package ex07_proxy_associations;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex07Team {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")   // MappedBy 속성의 값은
                                    // 연관관계의 주인인 Member.team
    private List<Ex07Member> members = new ArrayList<>();

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

    public List<Ex07Member> getMembers() {
        return members;
    }

    // 연관관계 편의 메소드
    public void addMember(Ex07Member member) {
        members.add(member);
    }
}
