package e_commerce.services;

import java.util.List;
import java.util.Optional;

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
// getById
	public Product getById(Long ProdId) {
		
		Optional<Product> prod=productRepo.findById(ProdId);
		
		return prod.get();
	}
	//update by id
	
	public Product updateProductById(Long id,Product prod) {
		
	Product existingProd=productRepo.findById(id).orElseThrow(()->new RuntimeException("product not found with id: "+ id));
	if (prod.getName() != null) existingProd.setName(prod.getName());
	if (prod.getPrice() != null) existingProd.setPrice(prod.getPrice());
	if (prod.getDescription() != null) existingProd.setDescription(prod.getDescription());
	if(prod.getCategory()!=null) existingProd.setCategory(prod.getCategory());
	if(prod.getImageUrl()!=null)existingProd.setImageUrl(prod.getImageUrl());
	if(prod.getStock()!=null)existingProd.setStock(prod.getStock());
	if(prod.getActive()!=null)existingProd.setActive(prod.getActive());

	
	return productRepo.save(existingProd);
	}
	
	//delete by id
	public void deleteById(Long id) {
		Product prod=productRepo.findById(id).orElseThrow(()->new RuntimeException("product not found with id: "+ id));
		productRepo.delete(prod);
	}
	
	
	
}
