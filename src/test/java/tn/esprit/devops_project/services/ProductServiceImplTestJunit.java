package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@Transactional
@Profile("test")
public class ProductServiceImplTestJunit {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    StockRepository stockRepository;
    @Test
    void addProduct() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Product retrievedProduct = productRepository.findById(addedProduct.getIdProduct()).orElse(null);
        assertNotNull(addedProduct.getIdProduct());
        assertEquals("Product1", retrievedProduct.getTitle());
        assertEquals(150.0f, retrievedProduct.getPrice());
        assertNotNull(retrievedProduct);
        assertEquals(addedProduct.getIdProduct(), retrievedProduct.getIdProduct());
        assertEquals(addedProduct.getPrice(), retrievedProduct.getPrice());
        assertEquals(addedProduct.getCategory(), retrievedProduct.getCategory());
    }
    @Test
    void retrieveProduct() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Long ProductId = addedProduct.getIdProduct();
        Product retrievedProduct = productService.retrieveProduct(ProductId);
        assertNotNull(retrievedProduct);
        assertEquals("Product1", retrievedProduct.getTitle());
        assertEquals(150.0f, retrievedProduct.getPrice());
        assertEquals(15, retrievedProduct.getQuantity());
    }
    @Test
    void retreiveAllProduct() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Stock stock2 = new Stock(2L, "stock2", null);
        Stock s2=stockRepository.save(stock2);
        Product product2 = new Product(1L, "Product2", 170.0f, 25, ProductCategory.BOOKS, s2);
        Product addedProduct2 = productService.addProduct(product2, s2.getIdStock());
        List<Product> products = productService.retreiveAllProduct();
        assertEquals(2, products.size());
        assertEquals("Product1", products.get(0).getTitle());
        assertEquals(150.0f, products.get(0).getPrice());
        assertEquals(15, products.get(0).getQuantity());
        assertEquals("Product2", products.get(1).getTitle());
        assertEquals(170.0f, products.get(1).getPrice());
        assertEquals(25, products.get(1).getQuantity());
    }
    @Test
    void deleteProduct() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Long addedProductId = addedProduct.getIdProduct();
        productService.deleteProduct(addedProductId);
        Product deletedProduct = productRepository.findById(addedProductId).orElse(null);
        assertNull(deletedProduct);
        assertFalse(productRepository.existsById(addedProductId));
    }

    @Test
    void retreiveProductStock() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Product product2 = new Product(1L, "Product2", 170.0f, 25, ProductCategory.BOOKS, s1);
        Product addedProduct2 = productService.addProduct(product2, s1.getIdStock());
        List<Product> products = productService.retreiveProductStock(s1.getIdStock());
        assertNotNull(products);
        assertEquals(2, products.size());

    }
    @Test
    void retrieveProductByCategory() {
        Stock stock = new Stock(1L, "stock1", null);
        Stock s1=stockRepository.save(stock);
        Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, s1);
        Product addedProduct = productService.addProduct(product, s1.getIdStock());
        Product product2 = new Product(1L, "Product2", 170.0f, 25, ProductCategory.BOOKS, s1);
        Product addedProduct2 = productService.addProduct(product2, s1.getIdStock());
        List<Product> products = productService.retrieveProductByCategory(ProductCategory.BOOKS);
        assertNotNull(products);
        assertEquals(2, products.size());
    }
}
