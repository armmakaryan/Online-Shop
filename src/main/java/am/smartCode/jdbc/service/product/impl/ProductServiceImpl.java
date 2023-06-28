package am.smartCode.jdbc.service.product.impl;

import am.smartCode.jdbc.exception.ProductNotFoundException;
import am.smartCode.jdbc.exception.ProductValidationException;
import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.repository.product.impl.ProductRepositoryImpl;
import am.smartCode.jdbc.service.product.ProductService;
import am.smartCode.jdbc.util.constants.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepositoryImpl productRepository;

    public ProductServiceImpl(ProductRepositoryImpl productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(String category, String name, long price) throws SQLException {
        productValidation(category, name, price);
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        productRepository.create(product);

    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        if (product.getId() <= 0) {
            throw new ProductValidationException(Message.INVALID_ID);
        }
        if (productRepository.get(product.getId()) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        productValidation(product.getCategory(), product.getName(), product.getPrice());
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            productRepository.update(product);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Product getProduct(long id) throws SQLException {
        Connection connection = productRepository.getConnection();
        if (id <= 0) {
            throw new ProductValidationException(Message.INVALID_ID);
        }
        if (productRepository.get(id) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        return productRepository.get(id);
    }

    @Override
    public void deleteProduct(long id) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        if (id <= 0) {
            throw new ProductValidationException(Message.INVALID_ID);
        }
        if (productRepository.get(id) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        try {
            productRepository.delete(id);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            connection.setAutoCommit(false);
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        Connection connection = productRepository.getConnection();
        return productRepository.getAll();
    }

    @Override
    public List<Product> getProductsByName(String name) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setReadOnly(true);
        return productRepository.findProductsByName(name);
    }

    private static void productValidation(String category, String name, long price) {
        if (category == null || category.isEmpty()) {
            throw new ProductValidationException(Message.BLANK_PRODUCT_CATEGORY);
        }
        if (name == null || name.isEmpty()) {
            throw new ProductValidationException(Message.BLANK_PRODUCT_NAME);
        }
        if (price <= 0) {
            throw new ProductValidationException(Message.INVALID_PRICE);
        }
    }
}