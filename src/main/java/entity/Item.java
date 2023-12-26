package entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Item {
    private String code;
    private String description;
    private double unitprice;
    private int qtyOnHand;
}
