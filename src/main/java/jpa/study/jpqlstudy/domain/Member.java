package jpa.study.jpqlstudy.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@NamedQuery(name = "Member.namedQuery", query = "select m from Member m where m.username =: username")
public class Member {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    private String username;

    private int age;
    @Enumerated(EnumType.STRING)
    private MemberType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDR_ID")
    private Order order;

    public void addTeam(Team team){
        this.team = team;
        team.getMembers().add(this);
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                '}';
    }
}
