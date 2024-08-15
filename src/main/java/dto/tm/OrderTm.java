package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class OrderTm extends RecursiveTreeObject<OrderTm> {
    private String code;
    private String desc;
    private int qty;
    private double amount;
    private JFXButton btn;

    // No-argument constructor
    public OrderTm() {
    }

    // All-argument constructor
    public OrderTm(String code, String desc, int qty, double amount, JFXButton btn) {
        this.code = code;
        this.desc = desc;
        this.qty = qty;
        this.amount = amount;
        this.btn = btn;
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

    // Getter for qty
    public int getQty() {
        return qty;
    }

    // Setter for qty
    public void setQty(int qty) {
        this.qty = qty;
    }

    // Getter for amount
    public double getAmount() {
        return amount;
    }

    // Setter for amount
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter for btn
    public JFXButton getBtn() {
        return btn;
    }

    // Setter for btn
    public void setBtn(JFXButton btn) {
        this.btn = btn;
    }

    // toString method
    @Override
    public String toString() {
        return "OrderTm{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", qty=" + qty +
                ", amount=" + amount +
                ", btn=" + btn +
                '}';
    }
}
