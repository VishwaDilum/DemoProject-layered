package dao.custom;

import java.sql.SQLException;

public interface PlaceOrderDao {
    int checkValidQty(String x) throws SQLException, ClassNotFoundException;
}
