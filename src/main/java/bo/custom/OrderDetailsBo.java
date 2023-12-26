package bo.custom;

import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.List;

public interface OrderDetailsBo {
    boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
    boolean updateQty(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException;
}
