package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * X To One(ManyToOne, OneToOne)
 *
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    /**
     * 엔티티를 반환
     *
     * 엔티티가 변경되면 API 스펙도 변경되는 문제가 존재한다.(엔티티를 직접 반환하면 안된다.)
     * N+1 문제가 존재한다.
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for(Order order : all) {
            order.getMember().getName();        // Lazy 강제 초기화
            order.getDelivery().getStatus();    // Lazy 강제 초기화
        }

        return all;
    }

    /**
     * Dto로 반환
     *
     * N+1 문제가 존재한다.
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());

        return result;
    }

    /**
     * Fetch 조인 활용
     *
     * N+1 문제를 해소한다.
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());

        return result;
    }

    /**
     * New 명령어를 통해 일반적인 SQL처럼 원하는 값을 선택해서 조회
     *
     * 장점
     *  - JPQL의 결과를 Dto로 즉시 변환할 수 있다.
     *  - 네트워크 용량을 최적화할 수 있다.(생각보다 미미하다.)
     *
     * 단점
     *  - Dto 조회를 제외한 재사용이 불가능하다.
     *  - 엔티티가 아니기 때문에 JPA의 기능을 사용할 수 없다.
     *  - API 스펙에 맞춘 코드가 리포지토리에 들어간다.
     */
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();         // Lazy 강제 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy 강제 초기화
        }
    }
}
