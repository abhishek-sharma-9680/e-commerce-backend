package e_commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;
import e_commerce.dto.PaymentDto;
import e_commerce.services.PaymentService;

@AllArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	private PaymentService paymentService;
	
	
	 @PostMapping
	    public ResponseEntity<PaymentDto> makePayment(@RequestBody PaymentDto paymentDto) {
	        PaymentDto savedPayment = paymentService.makePayment(paymentDto);
	        return ResponseEntity.status(HttpStatus.CREATED).body(savedPayment); // 201 Created
	    }

	    // Get payment details by orderId
	    @GetMapping("/{orderId}")
	    public ResponseEntity<PaymentDto> getPayment(@PathVariable Long orderId) {
	        PaymentDto payment = paymentService.getPaymentByOrderId(orderId);
	        if (payment == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if not found
	        }
	        return ResponseEntity.ok(payment); // 200 OK if found
	    }
}
