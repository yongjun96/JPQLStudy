package jpa.study.jpqlstudy.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MemberDTO {

    private String username;
    private int age;

    public MemberDTO(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
