package e_commerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import e_commerce.model.Product;
import e_commerce.services.ProductService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
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

@GetMapping("/getBy/{id}")
public ResponseEntity<Product>getProductById(@PathVariable Long id){
	Product product=productService.getById(id);
	return ResponseEntity.ok(product);
}

@PutMapping("/update/byId/{id}")
public ResponseEntity<Product>updateProd(@PathVariable Long id,@RequestBody Product prod){
	Product product=productService.updateProductById(id, prod);
	return ResponseEntity.ok(product);
}

@DeleteMapping("/delete/byId/{id}")
public ResponseEntity<String> deleteById(@PathVariable Long id){
	productService.deleteById(id);
	return ResponseEntity.ok(" product successfully deleted with id:"+id);
}

}
