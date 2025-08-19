package e_commerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.model.Product;
import e_commerce.services.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
	
	private ProductService productService;
	
@PostMapping("/create")
public ResponseEntity<Product>createProd(@RequestBody Product request){
	
	Product prod=productService.saveProduct(request);
	return ResponseEntity.ok(prod);
	
	}

@GetMapping("/getAll")
public ResponseEntity<List<Product>> getAllProd(){
	
	List<Product>prodList=productService.getAllProduct();
	return ResponseEntity.ok(prodList);
	
}

}
