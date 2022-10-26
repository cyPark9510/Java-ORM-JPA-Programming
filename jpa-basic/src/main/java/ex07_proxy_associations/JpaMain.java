package ex07_proxy_associations;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    static EntityManagerFactory emf;

    public static void main(String[] args) {
        emf = Persistence.createEntityManagerFactory("jpabook");

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Ex07Child child1 = new Ex07Child();
            Ex07Child child2 = new Ex07Child();

            Ex07Parent parent= new Ex07Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            em.persist(parent);
//            em.persist(child1);
//            em.persist(child2);

            em.flush();
            em.clear();

            Ex07Parent findParent = em.find(Ex07Parent.class, parent.getId());
            findParent.getChildList().remove(0);

            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void proxyTest(EntityManager em) {
        Ex07Team team = new Ex07Team();
        team.setName("jpa");
        em.persist(team);

        Ex07Member member = new Ex07Member();
        member.setUsername("hello");
        member.setTeam(team);
        em.persist(member);

        em.flush();
        em.clear();

//            Ex07Member findMember = em.find(Ex07Member.class, member.getId());
//            printMemberAndTeam(findMember);
//            printMember(findMember);



        Ex07Member refMember = em.getReference(Ex07Member.class, member.getId());
        System.out.println("refMember.class = " + refMember.getClass());
        System.out.println("Before: PersistenceUnit.isLoaded(findMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
        printMember(refMember);
        System.out.println("after: PersistenceUnit.isLoaded(findMember) = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

        Ex07Member findMemeber = em.find(Ex07Member.class, member.getId());
        System.out.println("findMemeber.class = " + findMemeber.getClass());
        System.out.println("refMember ==  findMemeber = " + (refMember == findMemeber));
        System.out.println("refMember instanceof Ex07Member = " + (refMember instanceof Ex07Member));

        System.out.println("findTeam = " + findMemeber.getTeam().getName());

        List<Ex07Member> members = em.createQuery("select m from Ex07Member m join fetch m.team", Ex07Member.class)
                .getResultList();
        // SQL: select * from Member
        // SQL: select * from Team where TEAM_ID = xxx
    }

    private static void printMember(Ex07Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Ex07Member member) {
        String Username = member.getUsername();
        System.out.println("Username = " + Username);

        Ex07Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
