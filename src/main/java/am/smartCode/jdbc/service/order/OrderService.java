package am.smartCode.jdbc.service.order;


public interface OrderService {
    void createOrder(Long userId, Long productId, int count) throws Exception;
}
