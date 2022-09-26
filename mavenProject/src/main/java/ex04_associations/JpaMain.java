package ex04_associations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            testSave(em);

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
        Ex04Team team1 = new Ex04Team("팀1");
        em.persist(team1);

        // 회원1 저장
        Ex04Member member1 = new Ex04Member("회원1");
        member1.setTeam(team1);
        em.persist(member1);

        // 회원2 저장
        Ex04Member member2 = new Ex04Member("회원2");
        member1.setTeam(team1);
        em.persist(member2);
    }

    public static void queryLogicJoin(EntityManager em) {
        String jpql = "select m from Ex04Member m join m.team t where t.name=:teamName";

        List<Ex04Member> resultList = em.createQuery(jpql, Ex04Member.class)
                .setParameter("teamName", "팀1")
                .getResultList();

        for(Ex04Member member : resultList) {
            System.out.println("[query] member.username = " + member.getUsername());
        }
    }

    public static void updateRelation(EntityManager em) {
        // 새로운 팀2
        Ex04Team team2 = new Ex04Team("팀2");
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Ex04Member member = em.find(Ex04Member.class, 1);
        member.setTeam(team2);
    }

    public static void deleteRelation(EntityManager em) {
        Ex04Member member = em.find(Ex04Member.class, 1);
        member.setTeam(null);
    }

    public static void unidirectional(EntityManager em) {
        Ex04Team team = new Ex04Team();
        team.setName("TeamA");
        em.persist(team);

        Ex04Member member = new Ex04Member();
        member.setUsername("member1");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

        Ex04Member findMember = em.find(Ex04Member.class, member.getId());
        Ex04Team findTeam = findMember.getTeam();

        System.out.println("findTeam = " + findTeam.getName());
    }

    public static void bidirectional(EntityManager em) {
        Ex04Team team = new Ex04Team();
        team.setName("TeamB");
        em.persist(team);

        Ex04Member member = new Ex04Member();
        member.setUsername("member2");
        member.changeTeam(team);
        em.persist(member);

//        em.flush();
//        em.clear();

        Ex04Team findTeam = em.find(Ex04Team.class, team.getId());
        List<Ex04Member> members = findTeam.getMembers();

        System.out.println("========");
        for(Ex04Member m : members){
            System.out.println("m = " + m.getUsername());
        }
        System.out.println("========");
    }
}