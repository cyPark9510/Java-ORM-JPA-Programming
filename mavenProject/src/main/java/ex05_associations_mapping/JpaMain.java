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
}