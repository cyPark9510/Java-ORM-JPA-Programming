package jpabook.jpashop;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpashop");

        EntityManager em = emf.createEntityManager();
        EntityTransaction et = em.getTransaction();

        et.begin();

        try {
            OrderItem orderItem = new OrderItem();
            em.persist(orderItem);

            Order order = new Order();
            order.addOrderItem(orderItem);

            em.persist(order);

            et.commit();
        } catch (Exception e) {
            et.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
