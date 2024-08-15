package dto;

import java.util.List;

public class OrderDto {
    private String orderId;
    private String date;
    private String custId;
    private List<OrderDetailsDto> list;

    // No-argument constructor
    public OrderDto() {
    }

    // All-argument constructor
    public OrderDto(String orderId, String date, String custId, List<OrderDetailsDto> list) {
        this.orderId = orderId;
        this.date = date;
        this.custId = custId;
        this.list = list;
    }

    // Getter for orderId
    public String getOrderId() {
        return orderId;
    }

    // Setter for orderId
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    // Getter for date
    public String getDate() {
        return date;
    }

    // Setter for date
    public void setDate(String date) {
        this.date = date;
    }

    // Getter for custId
    public String getCustId() {
        return custId;
    }

    // Setter for custId
    public void setCustId(String custId) {
        this.custId = custId;
    }

    // Getter for list
    public List<OrderDetailsDto> getList() {
        return list;
    }

    // Setter for list
    public void setList(List<OrderDetailsDto> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "orderId='" + orderId + '\'' +
                ", date='" + date + '\'' +
                ", custId='" + custId + '\'' +
                ", list=" + list +
                '}';
    }
}
