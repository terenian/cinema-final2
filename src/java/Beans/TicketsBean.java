package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;


/**
 *Provides Bean services to Ticket-related actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "TicketsBean")
@SessionScoped
public class TicketsBean implements Serializable {
    
    private ServiceInit service;
    private List<Ticket> myTickets;
    
    private int orderID;

    /**
     * @return orderID
     */
    public int getOrderID() {return orderID;}

    /**
     *Setter for an ordrID
     * @param orderID
     */
    public void setOrderID(int orderID) {this.orderID = orderID;}
    
    /**
     * Creates a new instance of UserTicketsBean
     */
    
    public TicketsBean() {
        
    }
    
    /**
     * 
     * @return currentUser tickets
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Throwable
     */
    public List<Ticket> getMyTickets() throws ClassNotFoundException, SQLException, Throwable {
        myTickets = ServiceInit.ticketsService().searchTicket(null, orderID, null, null, null);
        return myTickets;
    }
    
    /**
     * gets all tickets in a specific screening so we know what seats are taken / how many tickets left
     * @param screeningID
     * @return list of taken tickets
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws Throwable
     */
    public List<Ticket> getTicketsByScreening( int screeningID) throws ClassNotFoundException, SQLException, Throwable {
        myTickets = ServiceInit.ticketsService().searchTicket(null, null, screeningID, null, null);
        System.out.println("==Taken Tickets are: " + myTickets);
        return myTickets;
    }
    
    /**
     * marks a ticket as used
     * @param order
     * @param tickets
     * @return
     */
    /*
    public boolean markTicketsAsUsed( int order, int[][] tickets)  {
        boolean marked = false;
        try {
            for (int i=0; i< tickets.length; i++){
                ServiceInit.ticketsService().updateTicket(order, tickets[i][0], tickets[i][1]);
            }
            return true;
        } catch ( SQLException e){
            //logger.info (e + "marking ticketsFailed)
            return false;
        }
    }
    */
}



