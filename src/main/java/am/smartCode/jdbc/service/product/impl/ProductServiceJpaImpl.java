package am.smartCode.jdbc.service.product.impl;

import am.smartCode.jdbc.exception.ProductNotFoundException;
import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.service.product.ProductService;
import am.smartCode.jdbc.util.constants.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class ProductServiceJpaImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public void createProduct(@NotNull @NotBlank @NotEmpty String category,
                              @NotNull @NotBlank @NotEmpty String name,
                              @Positive long price) throws SQLException {
        Product product = new Product();
        product.setCategory(category);
        product.setName(name);
        product.setPrice(price);
        productRepository.create(product);
    }

    @Override
    public void updateProduct(@NotNull Product product) throws SQLException {
        if (productRepository.get(product.getId()) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        productRepository.update(product);
    }

    @Override
    public Product getProduct(@Positive long id) throws SQLException {
        if (productRepository.get(id) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        return productRepository.get(id);
    }

    @Override
    public void deleteProduct(@Positive long id) throws SQLException {
        if (productRepository.get(id) == null) {
            throw new ProductNotFoundException(Message.PRODUCT_NOT_FOUND);
        }
        productRepository.delete(id);
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        return productRepository.getAll();
    }

    @Override
    public List<Product> getProductsByName(@NotNull @NotBlank @NotEmpty String name) throws SQLException {
        return productRepository.findProductsByName(name);
    }
}
