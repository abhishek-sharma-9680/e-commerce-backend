package e_commerce.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;          // Link payment to order
    private Double amount;         // Amount paid
    private String method;         // e.g. CARD, UPI, NETBANKING
    private String status;         // e.g. PENDING, SUCCESS, FAILED
    private LocalDateTime createdAt;
}

