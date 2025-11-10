package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.OrderResponseDTO;
import ro.msg.learning.shop.dto.ProductQuantityDTO;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.OrderDetail;
import ro.msg.learning.shop.entity.Product;
import ro.msg.learning.shop.entity.keys.OrderDetailId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toEntity(CreateOrderDTO dto) {
        Order order = Order.builder()
                .id(UUID.randomUUID())
                .createdAt(dto.getOrderDate())
                .orderDetailList(new ArrayList<>())
                .build();

        DeliveryAddressMapper.mapDtoToOrder(dto.getDeliveryAddress(), order);
        return order;
    }

    public static OrderResponseDTO toDTO(Order order) {
        List<ProductQuantityDTO> products = new ArrayList<>();

        if(order.getOrderDetailList() != null) {
            for (OrderDetail detail : order.getOrderDetailList()) {
                ProductQuantityDTO pq = ProductQuantityDTO.builder()
                        .productId(detail.getProduct().getId())
                        .quantity(detail.getQuantity())
                        .build();
                products.add(pq);
            }
        }

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .orderDate(order.getCreatedAt())
                .deliveryAddress(DeliveryAddressMapper.mapOrderToDto(order))
                .locationId(order.getOrderDetailList().isEmpty() ? null : order.getOrderDetailList().getFirst().getShippedFrom().getId())
                .products(products)
                .build();
    }

    public static List<OrderDetail> toOrderDetails(List<ProductQuantityDTO> products, Order order) {
        return products.stream()
                .map(pq -> OrderDetail.builder()
                        .order(order)
                        .product(Product.builder()
                                .id(pq.getProductId())
                                .build())
                        .quantity(pq.getQuantity())
                        .orderDetailId(new OrderDetailId(order.getId(), pq.getProductId()))
                        .build())
                .collect(Collectors.toList());
    }
}
