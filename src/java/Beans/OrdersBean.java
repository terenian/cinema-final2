package Beans;

import DAOPackage.Configuration;
import DAOPackage.DBConnector;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import DAOPackage.OrdersService;
import EntitiesLayer.Order;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
/*
* @author Eran Z. & Itzik W
*/
@Named(value = "OredrsBean")
@Dependent
public class OrdersBean implements Serializable{

    OrdersService orderser;
    
    public OrdersBean() throws SQLException, Throwable {
        this.orderser = new OrdersService(new DBConnector(new Configuration()));
    }
    
    public ArrayList<Order> getAllOrders() throws SQLException
    {
        return orderser.searchOrders(null, null, null);
    }
    
}
