package am.smartCode.jdbc.service.order.impl;

import am.smartCode.jdbc.model.User;
import am.smartCode.jdbc.repository.order.OrderRepository;
import am.smartCode.jdbc.repository.product.ProductRepository;
import am.smartCode.jdbc.repository.user.UserRepository;
import am.smartCode.jdbc.service.order.OrderService;
import am.smartCode.jdbc.util.DatabaseConnection;

import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final DatabaseConnection databaseConnection;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public OrderServiceImpl(OrderRepository orderRepository,
                            DatabaseConnection databaseConnection,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.databaseConnection = databaseConnection;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void createOrder(Long userId, Long productId, int count) throws SQLException {
        var connection = databaseConnection.getConnection();
        try {
            connection.setAutoCommit(false);

            User user = userRepository.get(userId);
            var product = productRepository.get(productId);

            orderRepository.create(user, product, count);
            System.out.println("Your order has been confirmed");

            var totalPrice = count * product.getPrice();
            var newBalance = user.getBalance() - totalPrice;
            user.setBalance(newBalance);
            userRepository.update(user);
            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            System.out.println("Your account doesn't have enough money!!!");
        }
    }
}

