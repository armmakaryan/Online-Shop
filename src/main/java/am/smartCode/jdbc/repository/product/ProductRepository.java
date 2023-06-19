package am.smartCode.jdbc.repository.product;

import am.smartCode.jdbc.model.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {

    Connection getConnection();
    void create(Product product) throws SQLException;

    void update(Product product) throws SQLException;

    Product get(long id) throws SQLException;

    List<Product> getAll() throws SQLException;

    List<Product> findProductsByName(String name) throws SQLException;

    void delete(Long id) throws SQLException;
}
