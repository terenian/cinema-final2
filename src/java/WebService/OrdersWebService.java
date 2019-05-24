package WebService;

import Beans.ServiceInit;
import DAOPackage.CinemaLogger;
import EntitiesLayer.Ticket;
import EntitiesLayer.User;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

/**
 * This class provides a web service to Mark Tickets as used.
 * It validates the user, get tickets list by order id and marking the tickets
 * @author Eran Z. & Itzik W.
 */
@WebService(serviceName = "OrdersWebService")
public class OrdersWebService {
    
    private String username;
    private String password;
    private boolean userLogged;
    private int orderID;
    
    /**
     * web service method to check if user is valid
     * @param user
     * @param pass
     * @return true if user is valid, false if not
     */
    
    @WebMethod(operationName = "remoteValidateUser")
    public boolean remoteValidateUser(@WebParam(name = "username") String user, @WebParam(name = "password") String pass){
        this.username = user;
        this.password = pass;
        this.userLogged = false;
        this.orderID = 0;
        
        try {
            User userObj = ServiceInit.UsersService().validateUser(username, password);
            if (userObj!=null){
                userLogged = true;
                return true;
            }
        } catch (SQLException ex) {
            CinemaLogger.log(Level.SEVERE, this.getClass() + ex.getMessage());
        }
        return false;
    }
    
    
    
    /**
     * webService method to get the tickets of an order
     * @param order
     * @return int array of all tickets. Every ticket is 6 ints in that array. null if none found
     */
    @WebMethod(operationName = "getTickets")
    public  int[] getTickets(@WebParam(name = "order") int order)  {
        if (order > 0 && userLogged){
            this.orderID = order;
            try {
                ArrayList<Ticket> l = ServiceInit.ticketsService().searchTicket(null,orderID, null, null, null);
                //System.out.println("server: sent tickets are: " + l.toString());
                CinemaLogger.log(Level.INFO, this.getClass() + "server: sent tickets are: " + l.toString());
                int[] res = this.arrayListToListInteger(l);
                return res;
            } catch (SQLException ex) {
                CinemaLogger.log(Level.SEVERE, this.getClass() + ex.getMessage());            }
        }
        return null;
    }
    
    
    
    /**
     * webService to mark tickets as used. gets List of Tickets
     * @deprecated cuz we mark tickets by thir id.
     * @param tickets
     * @return true if 
     */
    
    @WebMethod(operationName = "useTickets")
    public boolean useTickets(@WebParam(name = "tickets")List<Integer> tickets) {
        System.out.println("server: Recived tickets to mark: " + tickets.toString());
        if(this.userLogged){
            for (int i=0; i<tickets.size(); i+=5){
                try {
                    ServiceInit.ticketsService().updateTicket(tickets.get(i), tickets.get(i+2), tickets.get(i+3));
                    
                } catch (SQLException ex) {
                    CinemaLogger.log(Level.SEVERE, this.getClass() + ex.getMessage());
                }
            }
            return true;
        }
        return false;
    }
    
    //reminder: Ticket form is Integer id[0], Integer sID[1], Integer row[2], Integer column[3] Intger used[4]
    /**
     * web service method to mark tickes as marked. gets list of tickets and mark it
     * @param tickets
     * @return true if marking succeed
     */
    @WebMethod(operationName = "useTicketsByTID")
    public boolean useTicketsByTID(@WebParam(name = "tickets") List<Integer> tickets) {
        //System.out.println("server: Recived ticketsID to mark: " + tickets.toString());
        CinemaLogger.log(Level.INFO, this.getClass() + "tickets to mark: " +tickets);
        if(this.userLogged){
            for (int i=0; i<tickets.size(); i++){
                try {
                    ServiceInit.ticketsService().updateTicketByID(tickets.get(i));
                } catch (SQLException ex) {
                    CinemaLogger.log(Level.SEVERE, this.getClass() + ex.getMessage());
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     *tests the webSerciese..
     * @param testParam
     * @return
     */
    @WebMethod(operationName = "test")
    public int[] test(@WebParam(name = "testParam") String testParam){
        //String[] arr = {"a","b", testParam};
        int[] arr = {21,2,6,7,8,Integer.parseInt(testParam)};
        System.out.println("Server arr is: " + Arrays.toString(arr));
        // ========
        return this.getTickets(Integer.parseInt(testParam));
    }
    
    /**
    * gets an array list of tickets and Parse it to int array of sequential tickets
    * It is needed cuz soap web service does not support complicated types (like ArrayList<Ticket>)
    * @return int Arrau wotj 
    */
    private int[] arrayListToListInteger(ArrayList<Ticket> l) {
        int[] intArr = new int[l.size()*6];
        int j=0;
        for (Ticket t: l){
            int[] tempArr = t.asIntArr();
            for (int i=0; i<6; i++){
                intArr[i+j] = tempArr[i];
                //System.out.print (intArr[i+j] + ", ");
            }
            j=j+6;
        }
        return intArr;
    }
    
    
}
