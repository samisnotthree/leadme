package com.leadme.core.service;

import com.leadme.core.entity.Member;
import com.leadme.core.entity.Orders;
import com.leadme.core.entity.Prog;
import com.leadme.core.entity.ProgDaily;
import com.leadme.core.entity.enums.OrderStatus;
import com.leadme.core.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    @DisplayName("주문 등록")
    @Transactional
    void addOrderTest() {
        //given
        Member member = Member.builder()
            .email("test@test.com")
            .name("testName")
            .pass("testPass")
            .phone("testPhone")
            .photo("testPhoto")
            .inDate(LocalDateTime.now())
            .outDate(null)
            .guide(null)
            .build();
        
        Orders order = Order.builder()
            .price(50000L)
            .payment("카드")
            .orderDate(LocalDateTime.now())
            .payDate(LocalDateTime.now())
            .status(OrderStatus.PAYED)
            .member(member)
            .progDaily(null)
            .build();

        //when
        Long addedOrder = orderService.addOrder(order);

        Orders foundOrder = orderRepository.findById(addedOrder).get();

        //then
        Assertions.assertThat(foundOrder).isSameAs(order);
    }
}
