package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



/**
 *Provides Bean services to Registration-related actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "UserOrdersBean")
@SessionScoped
public class UserOrdersBean implements Serializable {
    
    private List<Order> myOrders;  
    private int userID;
    
    /**
     * @return UserID
     */
    public int getUserID() {
        return userID;
    }        
        
    /**
     * Creates a new instance of UserOrdersBean.
     * Generates sets current session's user
     */
       
    public UserOrdersBean() {
        this.userID = SessionUtils.getUserId();
    }
   
    /**
     * @return list of current User's orders
     * @throws SQLException
     */
    public List<Order> getMyOrders() throws  SQLException {
        myOrders = ServiceInit.orderService().searchOrders(null, userID, null);
        return myOrders;
    }

    /**
     * gets the orderID from the Screening
     * @param orderID
     * @return
     * @throws SQLException
     */
    public Screening getOrderScreening(int orderID)throws  SQLException
    {
       int screeningID = ServiceInit.ticketsService().getScreeningIDByOrderID(orderID); 
       return (ServiceInit.screeningsService().searchScreenings(screeningID, null, null, null, null, null, null).get(0));
    }

    /**
     * @param orderID
     * @return List of all tickets in an order
     * @throws SQLException
     */
    public List<Ticket> getOrderTickets(int orderID)throws SQLException
    { 
       return (ServiceInit.ticketsService().searchTicket(null,orderID, null, null, null));
    }

    /**
     *
     * @param orderID
     * @return
     * @throws SQLException
     */
    public String getOrderMovie(int orderID)throws  SQLException
    {
       int screeningID = ServiceInit.ticketsService().getScreeningIDByOrderID(orderID); 
       return ServiceInit.moviesService().getMovieNameByScreeningID(screeningID); 
       
    }

    /**
     * Deletes an order and it's tickets by OrderID
     * @param orderID
     */
    public void deleteOrder(int orderID)
    {
        try{
            if(!ServiceInit.orderService().deleteOrder(orderID))
                      {
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage("there has been issue")); 
                      }
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass()+e.getMessage());
        }
    }
}



