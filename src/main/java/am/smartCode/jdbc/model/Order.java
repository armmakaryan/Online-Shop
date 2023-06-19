package am.smartCode.jdbc.model;

public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private double totalPrice;
    private int totalCountOfProduct;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCountOfProduct() {
        return totalCountOfProduct;
    }

    public void setTotalCountOfProduct(int totalCountOfProduct) {
        this.totalCountOfProduct = totalCountOfProduct;
    }

    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", userId=" + userId +
               ", productId=" + productId +
               ", totalPrice=" + totalPrice +
               ", totalCountOfProduct=" + totalCountOfProduct +
               '}';
    }
}