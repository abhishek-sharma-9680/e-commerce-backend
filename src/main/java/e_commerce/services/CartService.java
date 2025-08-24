package e_commerce.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import e_commerce.model.Cart;
import e_commerce.model.CartItem;
import e_commerce.model.Product;
import e_commerce.repository.CartRepo;
import e_commerce.repository.ProductRepo;
import e_commerce.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
@Transactional
public class CartService {

	private final CartRepo cartRepository;
    private final ProductRepo productRepository;
    private final UserRepo userRepository;
	
    
    
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId).orElseThrow());
                    return cartRepository.save(newCart);
                });
    }

    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUser(userId);
        Product product = productRepository.findById(productId).orElseThrow();

        // ✅ Check stock before adding to cart
        if (product.getStock() < quantity) {
            throw new RuntimeException("Not enough stock available for product: " + product.getName());
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            int newQuantity = existingItem.get().getQuantity() + quantity;

            if (product.getStock() < newQuantity) {
                throw new RuntimeException("Not enough stock for this quantity of product: " + product.getName());
            }

            existingItem.get().setQuantity(newQuantity);
        } else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }


    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUser(userId);
        cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        return cartRepository.save(cart);
    }
}
