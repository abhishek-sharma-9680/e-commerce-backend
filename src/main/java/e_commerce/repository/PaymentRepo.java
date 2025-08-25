package e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import e_commerce.model.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long>{
	
	Payment findByOrderId(Long orderId);

}
