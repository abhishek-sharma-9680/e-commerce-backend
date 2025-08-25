package e_commerce.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import e_commerce.dto.CartDto;
import e_commerce.dto.CartItemDto;
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
	
    
    
    public CartDto getCartByUser(Long userId) {
        Cart cart= cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId).orElseThrow());
                    return cartRepository.save(newCart);
                });
        List<CartItem>cartItemList=cart.getItems();
        
		List<CartItemDto>cartItemDtoList=new ArrayList<>();
		
		for(CartItem item:cartItemList) {
			CartItemDto dto=new CartItemDto();
			dto.setId(item.getId());
			dto.setPrice(item.getProduct().getPrice());
			dto.setProductId(item.getProduct().getProductId());
			dto.setProductName(item.getProduct().getName());
			dto.setQuantity(item.getQuantity());
			cartItemDtoList.add(dto);
		}


          CartDto cartDto=new CartDto();
          cartDto.setId(cart.getId());
          cartDto.setUserId(userId);
          cartDto.setItems(cartItemDtoList);
          return cartDto;
    }
    
//----------------------------------------------------------------------------------------------------------------------------    

    public CartDto addProductToCart(Long userId, Long productId, int quantity) {
    	
        Cart cart =  cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(userRepository.findById(userId).orElseThrow());
                   return cartRepository.save(newCart);
                });
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

        Cart savedCart=cartRepository.save(cart);
        
        List<CartItem>cartItemList=savedCart.getItems();
        
        		List<CartItemDto>cartItemDtoList=new ArrayList<>();
        		
        		for(CartItem item:cartItemList) {
        			CartItemDto dto=new CartItemDto();
        			dto.setId(item.getId());
        			dto.setPrice(item.getProduct().getPrice());
        			dto.setProductId(productId);
        			dto.setProductName(item.getProduct().getName());
        			dto.setQuantity(item.getQuantity());
        			cartItemDtoList.add(dto);
        		}
        
        
        CartDto cartDto=new CartDto();
        cartDto.setId(savedCart.getId());
        cartDto.setUserId(userId);
        cartDto.setItems(cartItemDtoList);
        return cartDto;
        
    }

//===============================================================================================================================
    
    public Cart removeProductFromCart(Long userId, Long productId) {
    	  Cart cart =  cartRepository.findByUserId(userId)
                  .orElseGet(() -> {
                      Cart newCart = new Cart();
                      newCart.setUser(userRepository.findById(userId).orElseThrow());
                     return cartRepository.save(newCart);
                  });
        cart.getItems().removeIf(item -> item.getProduct().getProductId().equals(productId));
        return cartRepository.save(cart);
    }
}
