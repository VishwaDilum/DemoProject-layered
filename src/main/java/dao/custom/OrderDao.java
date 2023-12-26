package dao.custom;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.CrudDao;
import dto.OrderDto;
import entity.Customer;
import entity.Item;
import entity.Orders;

import java.sql.SQLException;

public interface OrderDao extends CrudDao<Orders>{
    OrderDto lastOrder() throws SQLException, ClassNotFoundException;
    boolean save(OrderDto dto) throws SQLException, ClassNotFoundException;
}
