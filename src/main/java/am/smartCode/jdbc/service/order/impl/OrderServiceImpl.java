package am.smartCode.jdbc.service.order.impl;

import am.smartCode.jdbc.model.*;
import am.smartCode.jdbc.repository.order.OrderRepository;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.order.OrderService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createOrder(long userId, long productId, int count) throws SQLException {
        Connection connection = orderRepository.getConnection();
        connection.setAutoCommit(false);
        try {
            OrderValidation(userId, productId, count);
            Product product = productRepository.get(productId);
            User user = userRepository.get(userId);
            Order order = new Order();
            order.setUserId(userId);
            order.setProductId(productId);
            order.setCountOfProduct(count);
            order.setTotalPrice(count * product.getPrice());
            if (user.getBalance() < order.getTotalPrice()) {
                System.out.println("You doesn't have enough balance !!!");
                return;
            }
            user.setBalance(user.getBalance() - order.getTotalPrice());
            userRepository.update(user);
            orderRepository.create(order);
            connection.commit();
            System.out.println("Your order has been confirmed");
        } catch (Throwable e) {
            connection.rollback();
            connection.setAutoCommit(true);
            System.out.println("error");
        }

    }

    @Override
    public Order getOrderWithOrderId(long id) throws SQLException {
        Connection connection = orderRepository.getConnection();
        connection.setReadOnly(true);
        if (id <= 0) {
            throw new RuntimeException("Inserted ID must be > 0");
        }
        return orderRepository.getOrderWithOrderId(id);
    }

    @Override
    public List<Order> getAllOrdersOfOnePerson(long id) throws SQLException {
        Connection connection = orderRepository.getConnection();
        connection.setReadOnly(true);
        if (id <= 0) {
            throw new RuntimeException("Inserted ID must be > 0");
        }
        return orderRepository.getAllOrdersOfOnePerson(id);
    }

    @Override
    public List<Order> getAllOrders() throws SQLException {
        Connection connection = orderRepository.getConnection();
        connection.setReadOnly(true);
        return orderRepository.getAllOrders();
    }


    private static void
    OrderValidation(long userId, long productId, int count) {
        if (userId == 0) {
            throw new RuntimeException("Please enter user id");
        }
        if (productId == 0) {
            throw new RuntimeException("Please enter product id");
        }
        if (count == 0) {
            throw new RuntimeException("Please enter count of product");
        }
    }
}