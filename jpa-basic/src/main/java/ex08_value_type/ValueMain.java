package ex08_value_type;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Set;

public class ValueMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();

            Ex08Member member = new Ex08Member();
            member.setUsername("member");
            member.setHomeAddress(new Ex08Address("homeCity", "street", "123"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Ex08AddressEntity("old1",  "street", "123"));
            member.getAddressHistory().add(new Ex08AddressEntity("old2",  "street", "123"));

            em.persist(member);

            em.flush();
            em.clear();

            Ex08Member findMember = em.find(Ex08Member.class, member.getId());

//            List<Ex08Address> addressHistory = findMember.getAddressHistory();
//            for (Ex08Address address : addressHistory) {
//                System.out.println("address = " + address.getCity());
//            }

            // homeCity -> newCity
//            findMember.getHomeAddress().setCity("newCity");
            Ex08Address homeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Ex08Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoritFood = " + favoriteFood);
            }

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // old1 -> newCity1
//            findMember.getAddressHistory().remove(new Ex08Address("old1",  "street", "123"));  // 기본적으로 equals를 사용
//            findMember.getAddressHistory().remove(new Ex08Address("newCity1",  "street", "123"));

            et.commit();
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
    }

    private static void valueTypeTest(EntityManager em) {
        Ex08Member member1 = new Ex08Member();
        member1.setUsername("hello1");
        Ex08Address address = new Ex08Address("city", "street", "100");
        member1.setHomeAddress(address);
        member1.setWorkPeriod(new Ex08Period());
        em.persist(member1);

        Ex08Member member2 = new Ex08Member();
        member2.setUsername("hello2");
        Ex08Address copyAddress = new Ex08Address(address.getCity(), address.getStreet(), address.getZipcode());
        member2.setHomeAddress(copyAddress);
        member2.setWorkPeriod(new Ex08Period());
        em.persist(member2);

        System.out.println("address == copyAddress = " + (address == copyAddress));
        System.out.println("address.equals(copyAddress) = " + address.equals(copyAddress));

//        member2.getHomeAddress().setCity("newCity");
    }
}
