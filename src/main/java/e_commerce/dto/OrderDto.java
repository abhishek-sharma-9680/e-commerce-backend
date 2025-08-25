package e_commerce.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private String status; // e.g. PENDING, COMPLETED, CANCELLED
    private LocalDateTime orderDate;
    private List<OrderItemDto> items;
}