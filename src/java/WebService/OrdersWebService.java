package WebService;

import Beans.ServiceInit;
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
            Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    //webService to get the tikets of an order
    @WebMethod(operationName = "getTickets")
    public  int[] getTickets(@WebParam(name = "order") int order)  {
        if (order > 0 && userLogged){
            this.orderID = order;
            try {
                ArrayList<Ticket> l = ServiceInit.ticketsService().searchTicket(null,orderID, null, null, null);
                System.out.println("server: sent tickets are: " + l.toString());
                int[] res = this.arrayListToListInteger(l);
                return res;
            } catch (SQLException ex) {
                Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    /*webService to mark tickets as used.
    *reminder: Ticket form is Integer id[0], Integer sID[1], Integer row[2], Integer column[3] Intger used[4]
    */
    @WebMethod(operationName = "useTickets")
    public boolean useTickets(@WebParam(name = "tickets")List<Integer> tickets) {
        System.out.println("server: Recived tickets to mark: " + tickets.toString());
        if(this.userLogged){
            for (int i=0; i<tickets.size(); i+=5){
                try {
                    ServiceInit.ticketsService().updateTicket(tickets.get(i), tickets.get(i+2), tickets.get(i+3));
                    
                } catch (SQLException ex) {
                    System.out.println("server: exeption Cougth " + ex);
                    //Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
        return false;
    }
    
    @WebMethod(operationName = "useTicketsByTID")
    public boolean useTicketsByTID(@WebParam(name = "tickets") List<Integer> tickets) {
        System.out.println("server: Recived tickets to mark: " + tickets.toString());
        if(this.userLogged){
            for (int i=0; i<tickets.size(); i++){
                try {
                    ServiceInit.ticketsService().updateTicketByID(tickets.get(i));
                } catch (SQLException ex) {
                    System.out.println("server: exeption Cougth " + ex);
                    //Logger.getLogger(OrdersWebService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            return true;
        }
        return false;
    }
    
    
    @WebMethod(operationName = "test")
    public int[] test(@WebParam(name = "testParam") String testParam){
        //String[] arr = {"a","b", testParam};
        int[] arr = {21,2,6,7,8,Integer.parseInt(testParam)};
        System.out.println("Server arr is: " + Arrays.toString(arr));
        // ========
        return this.getTickets(Integer.parseInt(testParam));
    }
    
    /*gets an array list of tickets and returns int array of sequential tickets*/
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
