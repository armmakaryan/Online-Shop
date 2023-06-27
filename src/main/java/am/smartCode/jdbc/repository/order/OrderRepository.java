package am.smartCode.jdbc.repository.order;

import am.smartCode.jdbc.model.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {
    public Connection getConnection();
    void create(Order order) throws SQLException;

    Order getOrderWithOrderId(Long id) throws SQLException;

    List<Order> getAllOrdersOfOnePerson(Long id) throws SQLException;

    List<Order> getAllOrders() throws SQLException;

}