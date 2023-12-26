package dao.custom.impl;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.util.CrudUtil;
import db.DBConnection;
import dto.OrderDto;
import dao.custom.OrderDetailsDao;
import dao.custom.OrderDao;
import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    OrderDetailsDao orderDetailsModel = new OrderDetailsDaoImpl();


    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
        ResultSet resultSet = CrudUtil.execute(sql);

        if (resultSet.next()){
            return new OrderDto(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    null
            );
        }
        return null;
    }

    @Override
    public boolean save(OrderDto dto) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        return  CrudUtil.execute(sql,dto.getOrderId(),dto.getDate(),dto.getCustId());
    }

    @Override
    public boolean save(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Orders entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }
}
