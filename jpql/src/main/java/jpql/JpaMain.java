package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            functionTest(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }

    private static void jpqlTest(EntityManager em){
        Member member = new Member();
        member.setUsername("member");
        em.persist(member);

        em.flush();
        em.clear();

//            Query query = em.createQuery("SELECT m.username, m.age FROM Member m");
        Member result = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
                .setParameter("username", "member")
                .getSingleResult();
//            결과가 없으면: javax.persistence.NoResultException
//            둘 이상이면  : javax.persistence.NonUniqueResultException
//            Spring Data JPA -> 결과가 없으면 null 반환 또는 Optional 반환

        System.out.println("result = " + result.getUsername());
        result.setAge(20);

        MemberDTO memberInfo = em.createQuery("SELECT new jpql.MemberDTO(m.username, m.age) FROM Member m", MemberDTO.class)
                .getSingleResult();

        System.out.println("memberInfo = " + memberInfo.getUsername());
        System.out.println("memberInfo = " + memberInfo.getAge());
    }

    private static void pagingTest(EntityManager em) {
        for(int i=0; i<100; i++) {
            Member member = new Member();
            member.setUsername("member");
            member.setAge(i);
            em.persist(member);
        }

        em.flush();
        em.clear();

        List<Member> result = em.createQuery("SELECT m FROM Member m ORDER BY m.age desc", Member.class)
                .setFirstResult(10)
                .setMaxResults(20)
                .getResultList();

        System.out.println("result.size() = " + result.size());

        for (Member m : result) {
            System.out.println("member = " + m);
        }
    }

    private static void joinTest(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member");
        member.changeTeam(team);

        em.persist(member);

        String innerJoinQuery = "SELECT m FROM Member m INNER JOIN m.team t";
        String outerJoinQuery = "SELECT m FROM Member m LEFT JOIN m.team t";
        String crossJoinQuery = "SELECT m FROM Member m, Team t WHERE m.username = t.name";
        List<Member> result = em.createQuery(outerJoinQuery, Member.class)
                .getResultList();

        System.out.println("result.size() = " + result.size());
    }

    private static void joinFilterTest(EntityManager em) {
        Team team = new Team();
        team.setName("A");
        em.persist(team);

        Member member = new Member();
        member.setUsername("A");
        member.changeTeam(team);

        em.persist(member);

        // 조인 대상 필터링
//        String query = "SELECT m, t FROM Member m LEFT JOIN m.team t ON t.name = 'A'";

        // 연관관계 없는 엔티티 외부 조인
        String query = "SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name";

        List result = em.createQuery(query)
                .getResultList();

        for (Object o : result) {
            Object[] arr = (Object[]) o;
            System.out.println("member = " + arr[0]);
            System.out.println("team = " + arr[1]);
        }
    }

    private static void subQueryTest(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member");
        member.setAge(20);
        member.changeTeam(team);

        em.persist(member);

        String query = "SELECT (SELECT AVG(m1.age) FROM Member m1) as avgAge, t FROM Member m LEFT JOIN Team t on m.username = t.name";
        List<Object[]> ages = em.createQuery(query)
                .getResultList();

        for(Object[] o : ages) {
            System.out.println("age = " + o[0]);
        }
    }

    private static void jpqlTypeTest(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member");
        member.setAge(20);
        member.changeTeam(team);
        member.setType(MemberType.ADMIN);

        em.persist(member);

        String query = "SELECT m.username, 'HELLO', TRUE FROM Member m " +
                        "WHERE m.type = :userType";
        List<Object[]> result = em.createQuery(query)
                .setParameter("userType", MemberType.ADMIN)
                .getResultList();

        for(Object[] o : result) {
            System.out.println("o = " + o[0]);
            System.out.println("o = " + o[1]);
            System.out.println("o = " + o[2]);
        }
    }

    private static void caseTest(EntityManager em) {
        Team team = new Team();
        team.setName("team");
        em.persist(team);

        Member member = new Member();
        member.setUsername("member");

        em.persist(member);

        String query = "SELECT COALESCE(m.username, '이름 없는 회원') FROM Member m";
        List<String> result = em.createQuery(query, String.class)
                .getResultList();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    private static void functionTest(EntityManager em) {
        Member member1 = new Member();
        member1.setUsername("member1");

        em.persist(member1);

        Member member2 = new Member();
        member2.setUsername("member2");

        em.persist(member2);

        // 표준 함수
//        String query = "SELECT SUBSTRING(m.username, 4, 6) FROM Member m";

        // 사용자 정의 함수
        String query = "SELECT FUNCTION('group_concat', m.username) FROM Member m";
        List<String> result = em.createQuery(query, String.class)
                .getResultList();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }
}
