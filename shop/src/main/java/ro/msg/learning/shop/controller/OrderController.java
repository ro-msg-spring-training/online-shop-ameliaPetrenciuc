package ro.msg.learning.shop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.OrderResponseDTO;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.mapper.OrderMapper;
import ro.msg.learning.shop.service.impl.OrderServiceImpl;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderServiceImpl orderService;

    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderDTO dto){

        Order order=OrderMapper.toEntity(dto);
        order.setOrderDetailList(OrderMapper.toOrderDetails(dto.getProducts(),order));

        Order savedOrder=orderService.createOrder(order);

        OrderResponseDTO responseDTO= OrderMapper.toDTO(savedOrder);
        return ResponseEntity.ok(responseDTO);
    }
}
