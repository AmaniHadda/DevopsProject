package tn.esprit.devops_project.services.Iservices;

import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;

import java.util.List;

public interface IProductService {

<<<<<<< HEAD
    Product addProduct(Product product,long idStock);
=======
    Product addProduct(Product product, Long idStock);
>>>>>>> df2c7946e4860a60fe6d76fd3e8524d9430002d9
    Product retrieveProduct(Long id);
    List<Product> retreiveAllProduct();
    List<Product> retrieveProductByCategory(ProductCategory category);
    void deleteProduct(Long id);
    List<Product> retreiveProductStock(Long id);


}
