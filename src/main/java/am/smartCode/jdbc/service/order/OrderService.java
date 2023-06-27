package am.smartCode.jdbc.service.order;

import am.smartCode.jdbc.model.*;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    void createOrder(long userId, long productId, int count) throws SQLException;
    Order getOrderWithOrderId(long id) throws SQLException;
    List<Order> getAllOrdersOfOnePerson(long id) throws SQLException;
    List<Order> getAllOrders() throws SQLException;
}