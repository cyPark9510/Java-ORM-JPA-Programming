package ex05_associations_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Ex05Member member = new Ex05Member();
            member.setUsername("member1");

            em.persist(member);

            Ex05Team team = new Ex05Team();
            team.setName("teamA");
            team.getMembers().add(member);

            em.persist(team);

            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public static void testSave(EntityManager em) {
        // 팀1 저장
        Ex05Team team1 = new Ex05Team();
        team1.setName("팀1");
        em.persist(team1);

        // 회원1 저장
        Ex05Member member1 = new Ex05Member();
        member1.setUsername("회원1");
        member1.setTeam(team1);         // 연관관계 설정 member1 -> team1
        team1.getMembers().add(member1);// 연관관계 설정 team1 -> member1
        em.persist(member1);

        // 회원2 저장
        Ex05Member member2 = new Ex05Member();
        member2.setUsername("회원2");
        member2.setTeam(team1);         // 연관관계 설정 member2 -> team1
        team1.getMembers().add(member2);// 연관관계 설정 team1 -> member2
        em.persist(member2);
    }
}