package e_commerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce.model.Cart;

public interface CartRepo extends JpaRepository<Cart, Long>{

	
	 Optional<Cart> findByUserId(Long userId);
}
