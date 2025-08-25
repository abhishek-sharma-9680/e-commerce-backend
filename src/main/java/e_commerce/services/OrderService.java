package e_commerce.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import e_commerce.dto.OrderDto;
import e_commerce.dto.OrderItemDto;
import e_commerce.model.Cart;
import e_commerce.model.Order;
import e_commerce.model.OrderItem;
import e_commerce.repository.CartRepo;
import e_commerce.repository.OrderRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {

	 private final OrderRepo orderRepository;
	 private final CartRepo cartRepository;
	
//==================================================================================================================================
	    public OrderDto placeOrder(OrderDto orderDto) {
	    	
	        Order order = new Order();
	        order.setUserId(orderDto.getUserId());

	        Double totalAmount = orderDto.getItems().stream()
	                .mapToDouble(item -> item.getPrice() * item.getQuantity())
	                .sum();
	        order.setTotalAmount(totalAmount);

	        order.setCreatedAt(LocalDateTime.now());
	        order.setUpdatedAt(LocalDateTime.now());
	        order.setPaymentStatus(orderDto.getStatus());
	        order.setOrderStatus(orderDto.getStatus());

	        // Convert DTO items to entity items
	      List<OrderItem>itemList=new ArrayList<>();
	      
	     List<OrderItemDto>listItem= orderDto.getItems();
	     
	      for(OrderItemDto dto:listItem) {
	    	  
	    	  OrderItem item=new OrderItem();
	    	  item.setProductId(dto.getProductId());
	    	  item.setProductName(dto.getProductName());
	    	  item.setPrice(dto.getPrice());
	    	  item.setQuantity(dto.getQuantity());
	    	  item.setOrder(order);
	    	  itemList.add(item);
	      }
	      order.setOrderItems(itemList);
	        Order savedOrder = orderRepository.save(order);

	        // Convert saved entity back to DTO
	        return OrderDto.builder()
	                .orderId(savedOrder.getOrderId())
	                .userId(savedOrder.getUserId())
	                .totalAmount(savedOrder.getTotalAmount())
	                .status(savedOrder.getOrderStatus())
	                .orderDate(savedOrder.getCreatedAt())
	                .items(savedOrder.getOrderItems().stream().map(item -> OrderItemDto.builder()
	                        .productId(item.getProductId())
	                        .productName(item.getProductName())
	                        .price(item.getPrice())
	                        .quantity(item.getQuantity())
	                        .build()).collect(Collectors.toList()))
	                .build();
	    }

//-----------------------------------------------------------------------------------------------------------------------
	    
	    
	    
	    public OrderDto placeOrderFromCart(Long userId) {
	        // 1. Fetch cart for user
	        Cart cart = cartRepository.findByUserId(userId)
	                .orElseThrow(() -> new RuntimeException("Cart not found for user " + userId));

	        if (cart.getItems().isEmpty()) {
	            throw new RuntimeException("Cart is empty. Cannot place order.");
	        }

	        // 2. Convert cart items -> order items
	        List<OrderItem> orderItems = cart.getItems().stream().map(cartItem -> {
	            return OrderItem.builder()
	                    .productId(cartItem.getProduct().getProductId())
	                    .productName(cartItem.getProduct().getName())
	                    .price(cartItem.getProduct().getPrice())
	                    .quantity(cartItem.getQuantity())
	                    .build();
	        }).collect(Collectors.toList());

	        // 3. Calculate total
	        double totalAmount = orderItems.stream()
	                .mapToDouble(item -> item.getPrice() * item.getQuantity())
	                .sum();

	        // 4. Create Order
	        Order order = Order.builder()
	                .userId(userId)
	                .orderItems(orderItems)
	                .totalAmount(totalAmount)
	                .orderStatus("PENDING")
	                .paymentStatus("PENDING")
	                .build();

	        // set back-reference
	        orderItems.forEach(item -> item.setOrder(order));

	        // 5. Save order
	        Order savedOrder = orderRepository.save(order);
	        
	        OrderDto orderDto=new OrderDto();
	        
	        List<OrderItemDto>orderIteDtoList=new ArrayList<>();
	        List<OrderItem>orderItemList = savedOrder.getOrderItems();
	        
	        for(OrderItem orderItem:orderItemList) {
	        	
	        	OrderItemDto dto=new OrderItemDto();
	        	dto.setProductId(orderItem.getProductId());
	        	dto.setProductName(orderItem.getProductName());
	        	dto.setPrice(orderItem.getPrice());
	        	dto.setQuantity(orderItem.getQuantity());
	        	
	        	orderIteDtoList.add(dto);
	        	
	        }
	        orderDto.setItems(orderIteDtoList);
	        orderDto.setOrderId(savedOrder.getOrderId());
	        orderDto.setUserId(userId);
	        orderDto.setTotalAmount(savedOrder.getTotalAmount());
	        orderDto.setOrderDate(savedOrder.getCreatedAt());
	        orderDto.setStatus(savedOrder.getOrderStatus());

	        // 6. Clear cart
	      
	        cart.getItems().clear();
	        cartRepository.save(cart);

	        return orderDto;
	    }
	    
	    

//=============================================================================================================================== 
	    
	    
	    public OrderDto getOrderById(Long orderId) {
	        Order order=orderRepository.findById(orderId)
	                .orElseThrow(() -> new RuntimeException("Order not found"));
	        
	        List<OrderItem>orderItemList=order.getOrderItems();
	        List<OrderItemDto>dtoList=new ArrayList<>();
	        for(OrderItem item:orderItemList) {
	        	
	        	OrderItemDto dto=new OrderItemDto();
	        	dto.setProductId(item.getProductId());
	        	dto.setProductName(item.getProductName());
	        	dto.setPrice(item.getPrice());
	        	dto.setQuantity(item.getQuantity());
	        	dtoList.add(dto);
	        	
	        }
	        OrderDto orderDto=new OrderDto();
	        orderDto.setOrderDate(order.getCreatedAt());
	        orderDto.setOrderId(orderId);
	        orderDto.setStatus(order.getOrderStatus());
	        orderDto.setTotalAmount(order.getTotalAmount());
	        orderDto.setUserId(order.getUserId());
	        orderDto.setItems(dtoList);
	        
	        return orderDto;
	    }
	    
	    
//-------------------=========================================---------------------------------------============================
	    
	    
	    public List<OrderDto> getOrdersByUser(Long userId) {
	        List<Order> orderList=orderRepository.findByUserId(userId);
	        
	        List<OrderDto>orderDtoList=new ArrayList<>();
		       
		       for(Order order:orderList) {
		       
		       List<OrderItem>orderItemList=order.getOrderItems();
		        List<OrderItemDto>dtoList=new ArrayList<>();
		        for(OrderItem item:orderItemList) {
		        	
		        	OrderItemDto dto=new OrderItemDto();
		        	dto.setProductId(item.getProductId());
		        	dto.setProductName(item.getProductName());
		        	dto.setPrice(item.getPrice());
		        	dto.setQuantity(item.getQuantity());
		        	dtoList.add(dto);
		        	
		        }
		        OrderDto orderDto=new OrderDto();
		        orderDto.setOrderDate(order.getCreatedAt());
		        orderDto.setOrderId(order.getOrderId());
		        orderDto.setStatus(order.getOrderStatus());
		        orderDto.setTotalAmount(order.getTotalAmount());
		        orderDto.setUserId(order.getUserId());
		        orderDto.setItems(dtoList);
		        
		        orderDtoList.add(orderDto);
		        
		       }
		       return orderDtoList;
	    }
	    
	    
//----------=================================-----------------------------------=====================================----------
	    
	    public List<OrderDto> getAllOrders() {
	    	
	       List<Order>orderList=orderRepository.findAll();
	       
	       List<OrderDto>orderDtoList=new ArrayList<>();
	       
	       for(Order order:orderList) {
	       
	       List<OrderItem>orderItemList=order.getOrderItems();
	        List<OrderItemDto>dtoList=new ArrayList<>();
	        for(OrderItem item:orderItemList) {
	        	
	        	OrderItemDto dto=new OrderItemDto();
	        	dto.setProductId(item.getProductId());
	        	dto.setProductName(item.getProductName());
	        	dto.setPrice(item.getPrice());
	        	dto.setQuantity(item.getQuantity());
	        	dtoList.add(dto);
	        	
	        }
	        OrderDto orderDto=new OrderDto();
	        orderDto.setOrderDate(order.getCreatedAt());
	        orderDto.setOrderId(order.getOrderId());
	        orderDto.setStatus(order.getOrderStatus());
	        orderDto.setTotalAmount(order.getTotalAmount());
	        orderDto.setUserId(order.getUserId());
	        orderDto.setItems(dtoList);
	        
	        orderDtoList.add(orderDto);
	        
	       }
	       return orderDtoList;
	       
	    }

	  
	    public OrderDto updateOrderStatus(Long orderId, String status) {
	        OrderDto orderDto = getOrderById(orderId);
	        orderDto.setStatus(status);
	       Optional<Order> order= orderRepository.findById(orderId);
	       Order order1=order.get();
	       order1.setOrderStatus(status);
	       orderRepository.save(order1);
	       return orderDto;
	       
	    }
	
}
