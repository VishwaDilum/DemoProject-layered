package dao.custom.impl;

import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDetailsDto;
import dao.custom.OrderDetailsDao;
import entity.Item;
import entity.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailsDaoImpl implements OrderDetailsDao {


    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        boolean isDetailsSaved = true;
        for (OrderDetailsDto dto:list) {
            String sql = "INSERT INTO orderdetail VALUES(?,?,?,?)";
            Boolean S = CrudUtil.execute(sql,dto.getOrderId(),dto.getItemCode(),dto.getQty(),dto.getUnitPrice());

            if(!S){
                isDetailsSaved = false;
            }
        }
        return isDetailsSaved;
    }

    @Override
    public boolean updateQty(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        String sql2 = "SELECT qtyOnHand FROM item WHERE code = ?";
        String sql = "UPDATE item SET qtyOnHand = ? WHERE code = ?";

        for (OrderDetailsDto dto : list) {
            try (PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql2);
                 PreparedStatement pstmt = DBConnection.getInstance().getConnection().prepareStatement(sql)) {

                pstm.setString(1, dto.getItemCode());
                ResultSet resultSet = pstm.executeQuery();

                if (resultSet.next()) {
                    int currentQtyOnHand = resultSet.getInt(1);
                    pstmt.setInt(1, currentQtyOnHand - dto.getQty());
                    pstmt.setString(2, dto.getItemCode());
                    pstmt.executeUpdate();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return true; // Return true if the update was successful
    }

    @Override
    public boolean save(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetail entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<OrderDetail> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
