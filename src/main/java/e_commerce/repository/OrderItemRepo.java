package e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce.model.OrderItem;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long>{

}
