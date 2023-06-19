package am.smartCode.jdbc.repository.order.impl;

import am.smartCode.jdbc.model.Order;
import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.order.OrderRepository;
import am.smartCode.jdbc.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {
    private final Connection connection;

    public OrderRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS orders (
                            id bigserial primary key,
                            userId bigserial not null,
                            productId bigserial not null,
                            totalPrice double precision not null,
                            totalCountOfProduct int not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }
    }

    @Override
    public void create(User user, Product product, int count) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO orders (userId,productId,totalPrice,totalcountofproducts) VALUES (?,?,?,?)"
        );
        preparedStatement.setLong(1, user.getId());
        preparedStatement.setLong(2, product.getId());
        preparedStatement.setInt(4, count);

        double productPrice = product.getPrice();
        double totalPrice = count * productPrice;
            /*if (user.getBalance() < totalPrice){
                throw new Exception("Your account doesn't have enough money");
            }else {
                preparedStatement.setDouble(3, totalPrice);
                System.out.println("Your order has been confirmed");
            }*/
        if (user.getBalance() < totalPrice) {
            throw new Exception("Your account doesn't have enough money");
        }
        preparedStatement.setDouble(3, totalPrice);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void update(Order order) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE orders SET userId = ?, productId = ?, totalPrice = ?, totalcountofproducts = ? WHERE id = ?"
        );

        preparedStatement.setLong(1, order.getId());
        preparedStatement.setLong(2, order.getProductId());
        preparedStatement.setDouble(3, order.getTotalPrice());
        preparedStatement.setInt(4, order.getTotalCountOfProduct());
        preparedStatement.setLong(5, order.getId());

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public Order getOrderById(Long id) throws SQLException {
        Order order = new Order();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from orders WHERE userId = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setOrderFields(order, resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        return order;
    }

    @Override
    public List<Order> getAll() throws SQLException {
        List<Order> ordersList = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from orders");
        addProductToListFromResultSet(ordersList, resultSet);
        return ordersList;
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE from orders WHERE id = ?");
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    private void setOrderFields(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong("id"));
        order.setUserId(resultSet.getLong("userId"));
        order.setProductId(resultSet.getLong("productId"));
        order.setTotalPrice(resultSet.getDouble("totalPrice"));
        order.setTotalCountOfProduct(resultSet.getInt("totalCountOfProduct"));
    }

    private void addProductToListFromResultSet(List<Order> ordersList, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Order order = new Order();
            setOrderFields(order, resultSet);
            ordersList.add(order);
        }
    }
}
