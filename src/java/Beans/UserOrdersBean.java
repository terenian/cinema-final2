package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "UserOrdersBean")
@SessionScoped
public class UserOrdersBean implements Serializable {
    
    private ServiceInit service;
    private List<Order> myOrders;
    
    private int userID;        
    public int getUserID() {
        return userID;
    }        
    public void setUserID(int userID) {
        this.userID = userID;
    }
        
    /**
     * Creates a new instance of UserOrdersBean
     */
       
    public UserOrdersBean() {
        
    }
   
    public List<Order> getMyOrders() throws ClassNotFoundException, SQLException, Throwable {
        //List<Order> myOrders = null;
        myOrders = ServiceInit.orderService().searchOrders(null, userID, null);
        //OrdersService os = new OrdersService (new DBConnector(new Configuration()));
        //myOrders = os.searchOrders(null, userID, null);
        return myOrders;
    }
}



