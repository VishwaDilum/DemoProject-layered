package dto;

public class PlaceOrderDto {
    private String code;
    private int qty;

    // No-argument constructor
    public PlaceOrderDto() {
    }

    // All-argument constructor
    public PlaceOrderDto(String code, int qty) {
        this.code = code;
        this.qty = qty;
    }

    // Getter for code
    public String getCode() {
        return code;
    }

    // Setter for code
    public void setCode(String code) {
        this.code = code;
    }

    // Getter for qty
    public int getQty() {
        return qty;
    }

    // Setter for qty
    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "PlaceOrderDto{" +
                "code='" + code + '\'' +
                ", qty=" + qty +
                '}';
    }
}
