package ex04_associations;

import javax.persistence.*;

@Entity
public class Ex04Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")
//    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Ex04Team team;

    public Ex04Member() {
    }

    public Ex04Member(String username) {
        this.username = username;
    }

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

    public Ex04Team getTeam() {
        return team;
    }

    public void setTeam(Ex04Team team) {
        this.team = team;
    }

    // 연관관계 편의 메소드
    public void changeTeam(Ex04Team team) {
        this.team = team;

        team.getMembers().add(this);
    }
}