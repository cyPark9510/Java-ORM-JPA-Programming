package ex09_jpql_basic;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

//            jpqlTest(em);
//            criteriaTest(em);
            nativeSqlTest(em);

            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void jpqlTest(EntityManager em) {
        List<Ex09Member> result = em.createQuery(
                "select m from Ex09Member as m where m.username like '%kim%'",
                Ex09Member.class
        ).getResultList();

//        /*
//        select
//            m
//        from
//            Ex09Member m
//        where
//            m.username like '%kim%'
//        */
//        select
//            ex09member0_.MEMBER_ID as member_i1_22_,
//                ex09member0_.USERNAME as username2_22_
//        from
//            Ex09Member ex09member0_
//        where
//            ex09member0_.USERNAME like '%kim%'

    }

    private static void criteriaTest(EntityManager em) {
        // Criteria 사용 준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ex09Member> query = cb.createQuery(Ex09Member.class);

        // 루트 클래스(조회를 시작할 클래스)
        Root<Ex09Member> m = query.from(Ex09Member.class);

        // 쿼리 생성
        CriteriaQuery<Ex09Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
        List<Ex09Member> result = em.createQuery(cq).getResultList();

//        /* select
//            generatedAlias0
//        from
//            Ex09Member as generatedAlias0
//        where
//            generatedAlias0.username=:param0
//        */
//        select
//            ex09member0_.MEMBER_ID as member_i1_22_,
//            ex09member0_.USERNAME as username2_22_
//        from
//            Ex09Member ex09member0_
//        where
//            ex09member0_.USERNAME=?
    }

    private static void nativeSqlTest(EntityManager em) {
        Ex09Member member = new Ex09Member();
        member.setUsername("kim");
        em.persist(member);

        // flush -> commit, query

        // em.flush()

        // dbconn.executeQuery("select * from member");

        String sql = "select MEMBER_ID, username from Ex09Member where USERNAME = 'kim'";

        List<Ex09Member> result = em.createNativeQuery(sql, Ex09Member.class).getResultList();

//        /* dynamic native SQL query */
//        select
//            MEMBER_ID,
//            username
//        from
//            Ex09Member
//        where
//            USERNAME = 'kim'

        for (Ex09Member m : result) {
            System.out.println("m = " + m);
        }
    }
}
