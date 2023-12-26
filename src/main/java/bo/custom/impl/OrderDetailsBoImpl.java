package bo.custom.impl;

import bo.custom.OrderBo;
import bo.custom.OrderDetailsBo;
import dao.DaoFactory;
import dao.custom.OrderDetailsDao;
import dao.util.DaoType;
import dto.OrderDetailsDto;

import java.sql.SQLException;
import java.util.List;

public class OrderDetailsBoImpl implements OrderDetailsBo {
    private OrderDetailsDao orderDetailsDao = DaoFactory.getInstance().getDao(DaoType.ORDER_DETAIL);


    @Override
    public boolean saveOrderDetails(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        return  orderDetailsDao.saveOrderDetails(list);
    }

    @Override
    public boolean updateQty(List<OrderDetailsDto> list) throws SQLException, ClassNotFoundException {
        return orderDetailsDao.updateQty(list);
    }
}
