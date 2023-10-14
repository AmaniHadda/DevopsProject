package tn.esprit.devops_project.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;
import tn.esprit.devops_project.services.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    ProductRepository productRepository;
    @Mock
    StockRepository stockRepository;

    Stock stock = new Stock(1L, "stock1", null);
    Product product = new Product(1L, "Product1", 150.0f, 15, ProductCategory.BOOKS, stock);
    List<Product>productList=new ArrayList<Product>(){
        {
            add(new Product(1L,"Product1",150.0f,15, ProductCategory.BOOKS,stock));
            add(new Product(2L,"Product2",100.0f,20, ProductCategory.BOOKS,stock));
            add(new Product(3L,"Product3",120.0f,10, ProductCategory.BOOKS,stock));
        }
    };

    @Test
    void addProduct() {
        Mockito.when(stockRepository.findById(stock.getIdStock())).thenReturn(Optional.of(stock));
        Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
        Product productTest = productService.addProduct(product, stock.getIdStock());
        Assertions.assertNotNull(productTest);
    }

    @Test
    void retrieveProduct() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Product product1 = productService.retrieveProduct(1L);
        Assertions.assertNotNull(product1);
    }

    @Test
    void retreiveAllProduct() {

        Mockito.when(productRepository.findAll()).thenReturn(productList);
        //test
        List<Product>prodList=productService.retreiveAllProduct();
        Assertions.assertEquals(3, prodList.size());
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        Mockito.when(productRepository.findByCategory(Mockito.any())).thenReturn(productList);
        List<Product>prodListByCategory=productService.retrieveProductByCategory(ProductCategory.BOOKS);
        Assertions.assertEquals(3, prodListByCategory.size());
        Mockito.verify(productRepository, Mockito.times(1)).findByCategory(ProductCategory.BOOKS);
    }

    @Test
    void deleteProduct() {
        Mockito.doNothing().when(productRepository).deleteById(Mockito.anyLong());
        productService.deleteProduct(1L);
        Mockito.verify(productRepository,Mockito.times(1)).deleteById(1L);
    }

    @Test
    void retreiveProductStock() {

        Mockito.when(productRepository.findByStockIdStock(Mockito.anyLong())).thenReturn(productList);
        List<Product>prodListByStock=productService.retreiveProductStock(1L);
        Assertions.assertEquals(3, prodListByStock.size());
        Mockito.verify(productRepository, Mockito.times(1)).findByStockIdStock(1L);
    }
}