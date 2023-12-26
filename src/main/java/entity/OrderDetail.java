package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetail {
    private String orderid;
    private String itemcode;
    private int qty;
    private double unitprice;

}
