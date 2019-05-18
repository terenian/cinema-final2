package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import java.sql.SQLException;
import java.util.List;



/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "UserOrdersBean")
@SessionScoped
public class UserOrdersBean implements Serializable {
    
    private List<Order> myOrders;  
    private int userID;
    
    public int getUserID() {
        return userID;
    }        
        
    /**
     * Creates a new instance of UserOrdersBean
     */
       
    public UserOrdersBean() {
        this.userID = SessionUtils.getUserId();
    }
   
    public List<Order> getMyOrders() throws ClassNotFoundException, SQLException, Throwable {
        myOrders = ServiceInit.orderService().searchOrders(null, userID, null);
        return myOrders;
    }
    public Screening getOrderScreening(int orderID)throws ClassNotFoundException, SQLException, Throwable
    {
       int screeningID = ServiceInit.ticketsService().getScreeningIDByOrderID(orderID); 
       return (ServiceInit.screeningsService().searchScreenings(screeningID, null, null, null, null, null, null).get(0));
    }
    public List<Ticket> getOrderTickets(int orderID)throws ClassNotFoundException, SQLException, Throwable
    { 
       return (ServiceInit.ticketsService().searchTicket(orderID, null, null, null));
    }
    public String getOrderMovie(int orderID)throws ClassNotFoundException, SQLException, Throwable
    {
       int screeningID = ServiceInit.ticketsService().getScreeningIDByOrderID(orderID); 
       return ServiceInit.moviesService().getMovieNameByScreeningID(screeningID); 
       
    }
}



