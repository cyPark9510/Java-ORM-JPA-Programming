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

            unidirectional(em);
            bidirectional(em);

            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
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