package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Item {
    @Id
    private String code;
    private String description;
    private double unitPrice;
    private int qtyOnHand;

    @OneToMany(mappedBy = "item")
    private List<OrderDetail> orderDetails = new ArrayList<>();

    // No-argument constructor
    public Item() {
    }

    // All-argument constructor
    public Item(String code, String description, double unitPrice, int qtyOnHand) {
        this.code = code;
        this.description = description;
        this.unitPrice = unitPrice;
        this.qtyOnHand = qtyOnHand;
    }

    // Getter for code
    public String getCode() {
        return code;
    }

    // Setter for code
    public void setCode(String code) {
        this.code = code;
    }

    // Getter for description
    public String getDescription() {
        return description;
    }

    // Setter for description
    public void setDescription(String description) {
        this.description = description;
    }

    // Getter for unitPrice
    public double getUnitPrice() {
        return unitPrice;
    }

    // Setter for unitPrice
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Getter for qtyOnHand
    public int getQtyOnHand() {
        return qtyOnHand;
    }

    // Setter for qtyOnHand
    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    // Getter for orderDetails
    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    // Setter for orderDetails
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Item{" +
                "code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                ", qtyOnHand=" + qtyOnHand +
                '}';
    }
}
