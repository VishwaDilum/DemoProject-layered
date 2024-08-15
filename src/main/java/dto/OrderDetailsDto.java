package dto;

public class OrderDetailsDto {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;

    // No-argument constructor
    public OrderDetailsDto() {
    }

    // All-argument constructor
    public OrderDetailsDto(String orderId, String itemCode, int qty, double unitPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qty = qty;
        this.unitPrice = unitPrice;
    }

    // Getter for orderId
    public String getOrderId() {
        return orderId;
    }

    // Setter for orderId
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Getter for itemCode
    public String getItemCode() {
        return itemCode;
    }

    // Setter for itemCode
    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    // Getter for qty
    public int getQty() {
        return qty;
    }

    // Setter for qty
    public void setQty(int qty) {
        this.qty = qty;
    }

    // Getter for unitPrice
    public double getUnitPrice() {
        return unitPrice;
    }

    // Setter for unitPrice
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetailsDto{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
