package e_commerce.services;

import java.util.List;

import org.springframework.stereotype.Service;

import e_commerce.model.Product;
import e_commerce.repository.ProductRepo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {
	
	private ProductRepo productRepo;
	
	public Product saveProduct(Product prod) {
//		Product product=Product.builder()
//				.name(prod.getName())
//				.price(prod.getPrice())
//				.build();
		return productRepo.save(prod);
	}
	
	public List<Product> getAllProduct() {
		
		
		 List<Product>prodList=productRepo.findAll();
		 
//		 for(Product p:prodList) {
//			 Product obj= Product.builder()
//					      .name(p.getName())
//					      .price(p.getPrice())
//					      .build();
//			 
//			 listProd.add(obj);
//		 }
		 return prodList;
	}

}
