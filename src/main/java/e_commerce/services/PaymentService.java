package e_commerce.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import e_commerce.dto.PaymentDto;
import e_commerce.model.Payment;
import e_commerce.repository.PaymentRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class PaymentService {

	  private PaymentRepo paymentRepository;

	    
	    public PaymentDto makePayment(PaymentDto paymentDto) {
	        Payment payment = Payment.builder()
	                .orderId(paymentDto.getOrderId())
	                .amount(paymentDto.getAmount())
	                .method(paymentDto.getMethod())
	                .status("SUCCESS") // Mock success, later integrate with real gateway
	                .createdAt(LocalDateTime.now())
	                .build();

	        Payment saved = paymentRepository.save(payment);

	        return PaymentDto.builder()
	                .id(saved.getId())
	                .orderId(saved.getOrderId())
	                .amount(saved.getAmount())
	                .method(saved.getMethod())
	                .status(saved.getStatus())
	                .createdAt(saved.getCreatedAt())
	                .build();
	    }

	   
	    public PaymentDto getPaymentByOrderId(Long orderId) {
	    	
	        Payment payment = paymentRepository.findByOrderId(orderId);
	        if (payment == null) return null;

	        return PaymentDto.builder()
	                .id(payment.getId())
	                .orderId(payment.getOrderId())
	                .amount(payment.getAmount())
	                .method(payment.getMethod())
	                .status(payment.getStatus())
	                .createdAt(payment.getCreatedAt())
	                .build();
	    }
	
}
