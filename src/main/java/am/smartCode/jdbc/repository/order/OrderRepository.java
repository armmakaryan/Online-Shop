package am.smartCode.jdbc.repository.order;


import am.smartCode.jdbc.model.Order;
import am.smartCode.jdbc.model.Product;
import am.smartCode.jdbc.model.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {
    void create(User user, Product product, int count) throws Exception;

    void update(Order order) throws SQLException;

    Order getOrderById(Long id) throws SQLException;

    List<Order> getAll() throws SQLException;

    void delete(Long id) throws SQLException;
}
