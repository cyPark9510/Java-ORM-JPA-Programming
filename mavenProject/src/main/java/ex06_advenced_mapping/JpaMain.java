package ex06_advenced_mapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Ex06Movie movie = new Ex06Movie();
            movie.setActor("actor");
            movie.setDirector("director");
            movie.setName("바람과 함께 사라지다.");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

            Ex06Movie findMovie = em.find(Ex06Movie.class, movie.getId());
            System.out.println("findMovie = " + findMovie);

            em.flush();
            em.clear();

            Ex06Member member = new Ex06Member();
            member.setUsername("user");
            member.setCreatedBy("kim");
            member.setCreatedDate(LocalDateTime.now());

            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}