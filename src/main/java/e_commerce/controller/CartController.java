package e_commerce.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.model.Cart;
import e_commerce.services.CartService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {

	private CartService cartService;
	
	 @GetMapping("/{userId}")
	    public Cart getCart(@PathVariable Long userId) {
	        return cartService.getCartByUser(userId);
	    }

	    @PostMapping("/{userId}/add/{productId}")
	    public Cart addToCart(@PathVariable Long userId,
	                          @PathVariable Long productId,
	                          @RequestParam(defaultValue = "1") int quantity) {
	        return cartService.addProductToCart(userId, productId, quantity);
	    }

	    @DeleteMapping("/{userId}/remove/{productId}")
	    public Cart removeFromCart(@PathVariable Long userId,
	                               @PathVariable Long productId) {
	        return cartService.removeProductFromCart(userId, productId);
	    }
	
}
