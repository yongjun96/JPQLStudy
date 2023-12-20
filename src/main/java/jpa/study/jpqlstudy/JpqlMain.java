package jpa.study.jpqlstudy;

import jakarta.persistence.*;
import jpa.study.jpqlstudy.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JpqlMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();

        et.begin();

        try {

            List<Team> teamList = new ArrayList<>();
            List<Member> memberList = new ArrayList<>();

            for (int i = 1; i <= 4; i++) {

                Member member = new Member();

                if(i == 2){
                    member.setUsername("관리자");
                    member.setType(MemberType.ADMIN);
                    member.setAge(i);
                }else{
                    member.setUsername("김용준");
                    member.setType(MemberType.ADMIN);
                    member.setAge(i);
                }


                Team team = new Team();
                team.setName("용준팀");
                teamList.add(team);
                member.addTeam(team);
                em.persist(team);

                Order order = new Order();
                order.setOrderAmount(2);
                order.setAddress(new Address("city", "street", "zipcode"));
                member.setOrder(order);
                em.persist(order);

                Product product = new Product();
                product.setName("레고");
                product.setPrice(60000);
                product.setStockAmount(2);
                order.setProduct(product);
                em.persist(product);

                memberList.add(member);
                em.persist(member);
            }


            //String query = "select m.username, 'HELLO', TRUE from Member m ";
            //String query = "select m from Member m where type(m) = Member"; --> @DiscriminatorValue()이거 관련
            /*
            String query = "select case when m.age <= 10 then '학생요금'" +
                    "                   when m.age >= 60 then '경로요금'" +
                    "                   else '일반요금' end from Member m ";
            */
            //String query = "select coalesce(m.username, '이름 없는 회원') from Member m";
            //String query = "select nullif(m.username, '관리자') from Member m";
            //String query = "select 'A' || 'B' from Member m";  // CONCAT('A', 'B') 과 같은 기능 (하이버네이트)
            //String query = "select SUBSTRING(m.username, 1,2) from Member m";  // 문제열 자르기
            //String query = "select trim(m.username) from Member m"; // 공백 제거
            //String query = "select length(m.username) from Member m"; // 문자의 길이 출력
            //String query = "select locate('용', '김아무개용준') from Member m"; //해당 문자가 있는 순번 출력
            //String query = "select size(t.members) from Team t"; // 컬렉션의 크기를 반환


            /*
            String query = "Member.namedQuery";
            List<Member> memberQuery = em.createNamedQuery("Member.namedQuery", Member.class)
                    .setParameter("username", memberList.get(0).getUsername())
                    .getResultList();
            */

            String query = "update Member m set m.age = 20";

            int resultCount = em.createQuery("update Member m set m.age = 50 where m.id = 1").executeUpdate();

            em.clear();
            Member mem = em.find(Member.class, memberList.get(0).getId());

            System.out.println(mem.getAge());

            System.out.println(memberList.get(0).getAge());
            System.out.println(memberList.get(2).getAge());
            System.out.println(memberList.get(2).getAge());

            System.out.println("member : "+resultCount);

            et.commit();

        } catch (Exception e) {
            et.rollback();
        } finally {
            em.clear();
        }
        emf.close();
    }

}
