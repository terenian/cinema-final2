package WebService;

import Beans.ServiceInit;
import Beans.TicketsBean;
import EntitiesLayer.Ticket;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

/**
 *
 * @author Eran Z. & Itzik W.
 */
@WebService(serviceName = "OrdersWebService")
public class OrdersWebService {
    
    private String username;
    private String password;
    private boolean userLogged;
    private int orderID;
    
    /*web service to check if user is valid*/
    @WebMethod(operationName = "validateUser")
    public boolean validateUser(@WebParam(name = "username") String user, @WebParam(name = "password") String pass){
        this.username = user;
        this. password = pass;
        this.userLogged = false;
        this.orderID = 0;
        
        if (isValidUser() == true){
            userLogged = true;
            return true;
        } else{
            return false;
        }
    }
    
    //webService to get the tikets in an order
    @WebMethod(operationName = "getTickets")
    public List<Ticket> getTickets(@WebParam(name = "order") int order)  {
        if (order > 0 && userLogged){
            this.orderID = order;
            try {
                return ServiceInit.ticketsService().searchTicket(orderID, null, null, null);
            } catch (SQLException ex) {
                Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    //webService to mark tickets as used
    @WebMethod(operationName = "useTickets")
    public boolean useTickets(@WebParam(name = "tickets") List<Ticket> tickets) {
        System.out.println("server: in useTickets");
        System.out.println("server: List is: " + tickets.toString());
        if(this.userLogged){
            for (Ticket t : tickets){
                try {
                    ServiceInit.ticketsService().updateTicket(t.getOrderID(), t.getRowNum(), t.getColumnNum());
                } catch (SQLException ex) {
                     System.out.println("server: exeption Cougth " + ex);
                    //Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
        return false;
    }
    
    private boolean isValidUser(){
        return true;
    }
    
    
}
