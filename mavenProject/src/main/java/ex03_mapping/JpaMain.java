package ex03_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        logic(em);

        em.close();
        emf.close();
    }

    private static void logic(EntityManager em) {
        Ex03Board board = new Ex03Board();
        em.persist(board);
        System.out.println("board.id = " + board.getId());
    }
}
