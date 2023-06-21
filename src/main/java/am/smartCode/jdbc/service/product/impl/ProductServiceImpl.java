package am.smartCode.jdbc.service.product.impl;

import am.smartCode.jdbc.exception.ValidationException;
import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.service.product.ProductService;
import am.smartCode.jdbc.util.constants.Message;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(String category, String name, long price) throws SQLException {
        productValidation(category, name,price);
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        productRepository.create(product);

    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            productValidation(product.getCategory(), product.getName(),product.getPrice());
            productRepository.update(product);
            connection.commit();
            System.out.println("Product is updated");
        } catch (Throwable e) {
            connection.rollback();
            connection.setAutoCommit(true);
        }
    }

    @Override
    public Product getProduct(long id) throws SQLException {
        Connection connection = productRepository.getConnection();
        if (id <= 0) {
            throw new ValidationException(Message.INVALID_ID);
        }
        return productRepository.get(id);
    }

    @Override
    public void deleteProduct(long id) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            if (id <= 0) {
                throw new ValidationException(Message.INVALID_ID);
            }
            productRepository.delete(id);
            connection.commit();
            System.out.println("Product is deleted");
        } catch (Throwable e) {
            connection.rollback();
            connection.setAutoCommit(false);
        }
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setReadOnly(true);
        return productRepository.getAll();
    }

    @Override
    public List<Product> getProductsByName(String name) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setReadOnly(true);
        return productRepository.findProductsByName(name);
    }

    private static void productValidation(String category, String name,long price) {
        if (category.equals(null) || category.equals("")) {
            throw new ValidationException(Message.BLANK_PRODUCT_CATEGORY);
        }
        if (name.equals(null) || name.equals("")) {
            throw new ValidationException(Message.BLANK_PRODUCT_NAME);
        }
        if (price <= 0) {
            throw new ValidationException(Message.INVALID_PRICE);
        }
    }
}
