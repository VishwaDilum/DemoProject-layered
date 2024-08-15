package dto;

public class ItemDto {
    private String code;
    private String desc;
    private double unitPrice;
    private int qty;

    // No-argument constructor
    public ItemDto() {
    }

    // All-argument constructor
    public ItemDto(String code, String desc, double unitPrice, int qty) {
        this.code = code;
        this.desc = desc;
        this.unitPrice = unitPrice;
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

    // Getter for desc
    public String getDesc() {
        return desc;
    }

    // Setter for desc
    public void setDesc(String desc) {
        this.desc = desc;
    }

    // Getter for unitPrice
    public double getUnitPrice() {
        return unitPrice;
    }

    // Setter for unitPrice
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
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
        return "ItemDto{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                '}';
    }
}
