package e_commerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long userId;  // reference to User

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    private Double totalAmount;

    private String orderStatus;   // PENDING, CONFIRMED, SHIPPED, DELIVERED
    private String paymentStatus; // PENDING, PAID, FAILED, REFUNDED

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

   
}
