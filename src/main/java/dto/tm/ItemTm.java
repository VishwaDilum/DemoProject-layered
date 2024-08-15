package dto.tm;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class ItemTm extends RecursiveTreeObject<ItemTm> {
    private String code;
    private String desc;
    private double unitPrice;
    private int qty;
    private JFXButton btn;

    // No-argument constructor
    public ItemTm() {
    }

    // Parameterized constructor
    public ItemTm(String code, String desc, double unitPrice, int qty, JFXButton btn) {
        this.code = code;
        this.desc = desc;
        this.unitPrice = unitPrice;
        this.qty = qty;
        this.btn = btn;
    }

    // Getters and setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public JFXButton getBtn() {
        return btn;
    }

    public void setBtn(JFXButton btn) {
        this.btn = btn;
    }

    @Override
    public String toString() {
        return "ItemTm{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", unitPrice=" + unitPrice +
                ", qty=" + qty +
                ", btn=" + btn +
                '}';
    }
}
