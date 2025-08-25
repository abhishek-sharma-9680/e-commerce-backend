package e_commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.dto.CartDto;
import e_commerce.model.Cart;
import e_commerce.services.CartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

	private CartService cartService;
	
	
	
    // ✅ Get cart by userId
    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable Long userId) {
        CartDto cart = cartService.getCartByUser(userId);
        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if empty
        }
        return ResponseEntity.ok(cart); // 200 OK
    }

    // ✅ Add product to cart
    @PostMapping("/{userId}/add/{productId}")
    public ResponseEntity<CartDto> addToCart(@PathVariable Long userId,
                                             @PathVariable Long productId,
                                             @RequestParam(defaultValue = "1") int quantity) {
        CartDto updatedCart = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart); // 201 Created
    }

    // ✅ Remove product from cart
    @DeleteMapping("/{userId}/remove/{productId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long userId,
                                                  @PathVariable Long productId) {
        Cart updatedCart = cartService.removeProductFromCart(userId, productId);
        if (updatedCart == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if not found
        }
        return ResponseEntity.ok(updatedCart); // 200 OK
    }
    
}
