package am.smartCode.jdbc.repository.order.impl;

import am.smartCode.jdbc.model.Order;
import am.smartCode.jdbc.repository.order.OrderRepository;
import am.smartCode.jdbc.util.DatabaseConnection;
import am.smartCode.jdbc.util.constants.Strings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepositoryImpl implements OrderRepository {

    private final Connection connection;
    @Override
    public Connection getConnection(){
        return connection;
    }

    public OrderRepositoryImpl(DatabaseConnection databaseConnection) {
        this.connection = databaseConnection.getConnection();
        try {
            this.connection.createStatement().executeUpdate(
                    """
                            CREATE TABLE IF NOT EXISTS orders (
                            id bigserial primary key,
                            user_id bigint not null
                            constraint fk_order_user
                            references users,
                            product_id bigint not null
                            constraint fk_order_product
                            references products,
                            count_of_product integer not null,
                            total_price integer not null
                            )
                            """);
        } catch (SQLException e) {
            System.out.println("Connection exception");
        }
    }

    @Override
    public void create(Order order) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO orders (userid,productid,totalcountofproducts,totalprice) VALUES (?,?,?,?)"
        );
        preparedStatement.setLong(1, order.getUserId());
        preparedStatement.setLong(2, order.getProductId());
        preparedStatement.setInt(3, order.getCountOfProduct());
        preparedStatement.setLong(4, order.getTotalPrice());
        preparedStatement.executeUpdate();

        preparedStatement.close();
    }

    @Override
    public Order getOrderWithOrderId(Long id) throws SQLException {
        connection.setReadOnly(true);
        Order order = new Order();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from orders WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            setOrderFields(order, resultSet);
        }
        resultSet.close();
        preparedStatement.close();
        connection.setReadOnly(false);
        return order;
    }

    @Override
    public List<Order> getAllOrdersOfOnePerson(Long id) throws SQLException {
        List<Order> orders = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from orders WHERE userid = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        addOrderToListFromResultSet(orders, resultSet);
        return orders;
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        List<Order> orders = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT * from orders");
        addOrderToListFromResultSet(orders, resultSet);
        return orders;
    }

    private void setOrderFields(Order order, ResultSet resultSet) throws SQLException {
        order.setId(resultSet.getLong(Strings.ID));
        order.setUserId(resultSet.getLong(Strings.USER_ID));
        order.setProductId(resultSet.getLong(Strings.PRODUCT_ID));
        order.setCountOfProduct(resultSet.getInt(Strings.COUNT_OF_PRODUCT));
        order.setTotalPrice(resultSet.getLong(Strings.TOTAL_PRICE));
    }

    private void addOrderToListFromResultSet(List<Order> orders, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Order order = new Order();
            setOrderFields(order, resultSet);
            orders.add(order);
        }
    }
}