package e_commerce.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {
    private Long id;
    private Long orderId;
    private Double amount;
    private String method;
    private String status;
    private LocalDateTime createdAt;
}
