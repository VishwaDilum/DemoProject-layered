package dto.tm;

import java.util.Date;

public class OrdersTm {
    private String id;
    private Date date;
    private String customerid;

    // No-argument constructor
    public OrdersTm() {
    }

    // All-argument constructor
    public OrdersTm(String id, Date date, String customerid) {
        this.id = id;
        this.date = date;
        this.customerid = customerid;
    }

    // Getter for id
    public String getId() {
        return id;
    }

    // Setter for id
    public void setId(String id) {
        this.id = id;
    }

    // Getter for date
    public Date getDate() {
        return date;
    }

    // Setter for date
    public void setDate(Date date) {
        this.date = date;
    }

    // Getter for customerid
    public String getCustomerid() {
        return customerid;
    }

    // Setter for customerid
    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    // toString method
    @Override
    public String toString() {
        return "OrdersTm{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", customerid='" + customerid + '\'' +
                '}';
    }
}
