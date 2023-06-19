package am.smartCode.jdbc.service.product;

import am.smartCode.jdbc.model.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    void createProduct(String category, String name, String publishedDate, long price) throws SQLException;

    void updateProduct(Product product) throws SQLException;

    Product getProduct(long id) throws SQLException;

    void deleteProduct(long id) throws SQLException;

    List<Product> getAllProducts() throws SQLException;

    List<Product> getProductsByName(String name) throws SQLException;
}