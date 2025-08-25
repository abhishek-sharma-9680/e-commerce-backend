package e_commerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce.model.Order;

public interface OrderRepo extends JpaRepository<Order, Long>{

	   List<Order> findByUserId(Long userId);
}
