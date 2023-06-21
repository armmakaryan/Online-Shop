package am.smartCode.jdbc.repository.product.impl;

import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepositoryimpl implements ProductRepository {
    private final Connection connection;

    public ProductRepositoryimpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS products (
                            id bigserial primary key,
                            category varchar(255) not null,
                            name varchar(255) not null,
                            published_date varchar(255) not null ,
                            price integer not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }

    }


    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void create(Product product) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO products (id,category,name,price) VALUES (?,?,?)"
        );
        preparedStatement.setString(1, product.getCategory());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setLong(3, product.getPrice());

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    @Override
    public void update(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE products SET category = ?, name = ?, price = ? WHERE id = ?"
        );

        preparedStatement.setString(1, product.getCategory());
        preparedStatement.setString(2, product.getName());
        preparedStatement.setLong(3, product.getPrice());
        preparedStatement.setLong(4, product.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Product get(long id) throws SQLException {
        Product product = new Product();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from products WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setProductFields(product, resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return product;
    }

    @Override
    public List<Product> getAll() throws SQLException {
        List<Product> productsList = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from products");
        addProductToListFromResultSet(productsList, resultSet);
        resultSet.close();

        return productsList;
    }


    @Override
    public List<Product> findProductsByName(String name) throws SQLException {

        List<Product> products = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM products WHERE lower(name) LIKE lower(concat('%',?,'%'))"
        );
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        addProductToListFromResultSet(products, resultSet);
        preparedStatement.close();
        resultSet.close();
        return products;
    }

    @Override
    public void delete(Long id) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from products WHERE id = ?");
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    private void setProductFields(Product product, ResultSet resultSet) throws SQLException {
        product.setId(resultSet.getLong("id"));
        product.setCategory(resultSet.getString("category"));
        product.setName(resultSet.getString("name"));
        product.setPrice(resultSet.getLong("price"));
    }

    private void addProductToListFromResultSet(List<Product> productList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Product product = new Product();
            setProductFields(product, resultSet);
            productList.add(product);
        }
    }
}