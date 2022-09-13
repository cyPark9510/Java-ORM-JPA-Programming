package ex01_start;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory 생성
        // 애플리케이션 실행시 한번만 생성해서 공유해서 사용
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        JpaMain jm = new JpaMain();
        jm.transactionManager(emf);

        // 애플리케이션 종료시점에 닫아 줘야한다.
        emf.close();
    }

    private void transactionManager(EntityManagerFactory emf) {
        // EntityManager 생성
        // 요청이 올 때마다 생성하고 사용 후, 반드시 종료해야 한다.
        EntityManager em = emf.createEntityManager();

        // EntityTransaction 획득
        // 데이터를 변경하는 모든 작업은 Transaction 안에서 진행해야 한다.
        EntityTransaction et = em.getTransaction();

        try {
            // Transaction 시작
            et.begin();

            // 비즈니스 로직
            save(em);
            select(em);
            update(em);
            selectByJPQL(em);
            selectResultPagingByJPQL(em);
            delete(em);

            // Transaction 커밋
            et.commit();
        } catch (Exception e) {
            // 예외 발생 시, Transaction 롤백
            et.rollback();
        } finally {
            // EntityManager 종료
            em.close();
        }

    }

    private void save(EntityManager em) {
        Ex01Member member = new Ex01Member();
        member.setId(1L);
        member.setName("Hello");

        em.persist(member);
    }

    private void select(EntityManager em) {
        Ex01Member findMember = em.find(Ex01Member.class, 1L);
        System.out.println("findMember.id = " + findMember.getId());
        System.out.println("findMember.name = " + findMember.getName());
    }

    private void update(EntityManager em) {
        Ex01Member findMember = em.find(Ex01Member.class, 1L);
        findMember.setName("HelloJPA");
    }

    private void delete(EntityManager em) {
        Ex01Member findMember = em.find(Ex01Member.class, 1L);

        em.remove(findMember);
    }

    private void selectByJPQL(EntityManager em) {
        List<Ex01Member> findMembers = em.createQuery("select m from Ex01Member as m", Ex01Member.class)
                .getResultList();

        for(Ex01Member member : findMembers) {
            System.out.println("member.name = " + member.getName());
        }
    }

    private void selectResultPagingByJPQL(EntityManager em) {
        List<Ex01Member> findMembers = em.createQuery("select m from Ex01Member as m", Ex01Member.class)
                .setFirstResult(5)
                .setMaxResults(10)
                .getResultList();

        for(Ex01Member member : findMembers) {
            System.out.println("member.name = " + member.getName());
        }
    }
}