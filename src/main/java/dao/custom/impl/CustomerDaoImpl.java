package dao.custom.impl;

import dao.util.CrudUtil;
import dao.util.HibernateUtil;
import db.DBConnection;
import dto.CustomerDto;
import dao.custom.CustomerDao;
import entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {



    @Override
    public CustomerDto searchCustomer(String id) {
        return null;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
//        String sql = "INSERT INTO customer VALUES(?,?,?,?)";
//            return CrudUtil.execute(sql,entity.getId(),entity.getName(),entity.getAddress(),entity.getSalary());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.find(Customer.class, entity.getId());
        customer.setName(entity.getName());
        customer.setAddress(entity.getAddress());
        customer.setSalary(entity.getSalary());
        session.save(customer);
        transaction.commit();
        return true;

        //String sql = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        //return CrudUtil.execute(sql,entity.getName(),entity.getAddress(),entity.getSalary(),entity.getId());
    }

    @Override
    public boolean delete(String entity) throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.find(Customer.class,entity));
        transaction.commit();
        return true;
    }

    @Override
    public List<Customer> getAll() throws SQLException, ClassNotFoundException {
        Session session = HibernateUtil.getSession();
        Query query = session.createQuery("FROM Customer");
        List<Customer> list = query.list();



//        List<Customer> list = new ArrayList<>();
//        String sql = "SELECT * FROM customer";
//        ResultSet resultSet = CrudUtil.execute(sql);
//        while (resultSet.next()){
//            list.add(new Customer(
//                    resultSet.getString(1),
//                    resultSet.getString(2),
//                    resultSet.getString(3),
//                    resultSet.getDouble(4)
//            ));
//        }
        return list;
    }
}
