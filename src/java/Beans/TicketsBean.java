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
@Named(value = "TicketsBean")
@SessionScoped
public class TicketsBean implements Serializable {
    
    private ServiceInit service;
    private List<Ticket> myTickets;
    
    private int orderID;        
    public int getOrderID() {return orderID;}        
    public void setOrderID(int orderID) {this.orderID = orderID;}
        
    /**
     * Creates a new instance of UserTicketsBean
     */
       
    public TicketsBean() {
        
    }
   
    public List<Ticket> getMyTickets() throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        myTickets = ServiceInit.ticketsService().searchTicket(orderID, null, null, null);
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return myTickets;
    }
    
    public List<Ticket> getTicketsByScreening( int screeningID) throws ClassNotFoundException, SQLException, Throwable {
        myTickets = ServiceInit.ticketsService().searchTicket(null, screeningID, null, null);
        System.out.println("==Taken Tickets are: " + myTickets);
        return myTickets;
    }
    
}



