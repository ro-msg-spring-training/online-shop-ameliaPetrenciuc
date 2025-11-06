package ro.msg.learning.shop.mapper;

import ro.msg.learning.shop.dto.CreateOrderDTO;
import ro.msg.learning.shop.dto.OrderResponseDTO;
import ro.msg.learning.shop.dto.ProductQuantityDTO;
import ro.msg.learning.shop.entity.Order;
import ro.msg.learning.shop.entity.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Order toEntity(CreateOrderDTO dto) {
        return Order.builder()
                .createdAt(dto.getOrderDate())
                .country(dto.getDeliveryAddress().getCountry())
                .city(dto.getDeliveryAddress().getCity())
                .county(dto.getDeliveryAddress().getCounty())
                .street(dto.getDeliveryAddress().getStreet())
                .orderDetailList(new ArrayList<>())
                .build();
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
}
