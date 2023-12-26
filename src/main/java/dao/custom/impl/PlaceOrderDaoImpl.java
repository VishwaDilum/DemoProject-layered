package dao.custom.impl;

import db.DBConnection;
import dao.custom.PlaceOrderDao;

import java.sql.*;

import static java.lang.Integer.parseInt;

public class PlaceOrderDaoImpl implements PlaceOrderDao {

    @Override
    public int checkValidQty(String x) throws SQLException, ClassNotFoundException {
        String sql = "SELECT qtyOnHand FROM item WHERE code = ?";
        int qtyOnHand = 0;
        try (PreparedStatement pstmt = DBConnection.getInstance().getConnection().prepareStatement(sql)) {
            pstmt.setString(1, x);

            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                qtyOnHand = resultSet.getInt("qtyOnHand");
                System.out.println("Qty On Hand: " + qtyOnHand);
            }
        }
        return qtyOnHand;
    }
}
