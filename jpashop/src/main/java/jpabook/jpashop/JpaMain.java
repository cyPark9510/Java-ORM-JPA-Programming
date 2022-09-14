package jpabook.jpashop;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();

        try {
            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
