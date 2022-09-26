package ex05_associations_mapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ex05Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // 다대일 양방향
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)
    private Ex05Team team;

    // 일대일 양방향
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Ex05Locker locker;

    // 다대다 양방향
    @OneToMany(mappedBy = "member")
    private List<Ex05MemberProduct> mamberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}