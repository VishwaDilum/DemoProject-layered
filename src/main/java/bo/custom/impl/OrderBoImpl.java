package bo.custom.impl;

import bo.custom.OrderBo;
import dao.DaoFactory;
import dao.custom.ItemDao;
import dao.custom.OrderDao;
import dao.custom.OrderDetailsDao;
import dao.util.CrudUtil;
import dao.util.DaoType;
import db.DBConnection;
import dto.OrderDto;
import entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderBoImpl implements OrderBo {
    private OrderDao orderDao = DaoFactory.getInstance().getDao(DaoType.ORDER);

    @Override
    public boolean saveOrder(OrderDto dto) throws SQLException, ClassNotFoundException {
        return  orderDao.save(dto);
    }

    @Override
    public OrderDto lastOrder() throws SQLException, ClassNotFoundException {
        return orderDao.lastOrder();
    }
}
