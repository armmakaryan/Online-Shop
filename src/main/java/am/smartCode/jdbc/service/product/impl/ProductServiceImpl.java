package am.smartCode.jdbc.service.product.impl;

import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.service.product.ProductService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void createProduct(String category, String name, String publishedDate, long price) throws SQLException {
        productValidation(category, name, publishedDate, price);
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPublishedDate(publishedDate);
        product.setPrice(price);
        productRepository.create(product);

    }

    @Override
    public void updateProduct(Product product) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            productValidation(product.getCategory(), product.getName(), product.getPublishedDate(), product.getPrice());
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
            throw new RuntimeException("Inserted ID must be > 0");
        }
        return productRepository.get(id);
    }

    @Override
    public void deleteProduct(long id) throws SQLException {
        Connection connection = productRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            if (id <= 0) {
                throw new RuntimeException("Inserted ID must be > 0");
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

    private static void productValidation(String category, String name, String publishedDate, long price) {
        if (category.equals(null) || category.equals("")) {
            throw new RuntimeException("Please enter product category");
        }
        if (name.equals(null) || name.equals("")) {
            throw new RuntimeException("Please enter product name");
        }
        if (!Pattern.compile("^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$").matcher(publishedDate).matches()) {
            throw new RuntimeException("Please enter valid published year ");
        }
        if (price <= 0) {
            throw new RuntimeException("Please enter price of product");
        }
    }
}
