package ex02_persistence_context;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        // EntityManagerFactory 생성
        // 애플리케이션 실행시 한번만 생성해서 공유해서 사용
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");

        lifeCycleManager(emf);

        // 애플리케이션 종료시점에 닫아 줘야한다.
        emf.close();
    }

    public static void lifeCycleManager(EntityManagerFactory emf) {
        // EntityManager 생성과 동시에 영속성 컨텍스트도 생성
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Ex02Member member1 = create("member1");
            managed(em, member1);
            /*
            https://www.inflearn.com/questions/304787

            이것은 하이버네이트 내부 버그일 가능성이 높습니다.
            오류 메시지를 잘 보시면 다음과 같이 하이버네이트 버그를 나타낼 수 있다는 표현이 있습니다.
            HHH000099: an assertion failure occurred (this may indicate a bug in Hibernate, but is more likely due to unsafe use of the session)
            참고로 다음에서 flush()를 중간에 호출해주면 정상 동작하는데요. 아마도 하이버네이트 내부 프로세스 이벤트와 관련된 버그로 보입니다.

            실제 detach를 하고 이후에 또 merge를 호출하도록 사용하는 일은 없기 때문에 이 부분은 크게 고민하지 않으셔도 됩니다.
             */
            em.flush();
            detached(em, member1);

            merge(em, member1);
            remove(em, member1);

            Ex02Member member2 = create("member2");
            managed(em, member2);

            TypedQuery<Ex02Member> query = em.createQuery("select m from Ex02Member m", Ex02Member.class);
            // JPQL 실행 시, 즉시 flush() 호출
            List<Ex02Member> members = query.getResultList();

            for(Ex02Member member : members) {
                System.out.println("member = " + member.toString());
            }

            // 1차 캐시에 있으면 1차 캐시에서 반환
            // 1차 캐시에 없으면 DB에서 조회 -> 영속 -> 반환
            Ex02Member findMember = em.find(Ex02Member.class, member2.getId());
            System.out.println("findMember.username = " + findMember.getUsername());
            System.out.println("findMember == member2 = " + (findMember == member2));
            update(findMember);

            remove(em, member2);

            Ex02Member member3 = create("member3");
            managed(em, member3);
            Ex02Member member4 = create("member4");
            managed(em, member4);

            Ex02Member member5 = create("member5");
            merge(em, member5);

            // 영속성 컨텍스트 초기화
            // 1차 캐시, 쓰기 지연 SQL 저장소 초기화
            em.clear();

            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            // EntityManager + 영속성 컨텍스트 모두 종료
            em.close();
        }
    }

    public static Ex02Member create(String id) {
        // 비영속
        return new Ex02Member(id, "회원", 10);
    }

    public static void managed(EntityManager em, Ex02Member member) {
        // 영속
        // 영속성 컨텍스트가 관리
        em.persist(member);
    }

    public static void detached(EntityManager em, Ex02Member member) {
        // 준영속
        em.detach(member);
    }

    public static void remove(EntityManager em, Ex02Member member) {
        // 삭제
        em.remove(member);
    }

    public static void update(Ex02Member member) {
        // 변경감지
        member.setUsername("hi");
        member.setAge(20);
    }

    public static void merge(EntityManager em, Ex02Member member) {
        // 병합
        // 비영속/준영속 모두 영속상태로 변경
        em.merge(member);
    }
}
