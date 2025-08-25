package e_commerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {
	
    private Long id;
    private Long productId;
    private String productName;
    private Double price;
    private int quantity;
    
}
