/*
* Main TicketsProject Class
*/
package Beans;

import DAOPackage.Configuration;
import DAOPackage.DBConnector;
import EntitiesLayer.Order;
import DAOPackage.OrdersService;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Eran Z. & Itzik W
 */
public class TicketsProjectMain {
    private static DBConnector dbcon;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException, Throwable {
        
        Configuration c = new Configuration();
        System.out.println("After ConfigurationL "+ c.getDbName()+ " " +c.getDbServer() + " " + c.getUserName() + " " +c.getPassword());
        dbcon = new DBConnector(c);
        
        test();
        
        dbcon.closeConnection();
    }
    
    
    
    
    private static void test() throws SQLException, ClassNotFoundException {
        System.out.println(" insert ===============");
        
        OrdersService os = new OrdersService(dbcon);
        os.insertOrder(1, 20);
        //os.insertOrder(2, 22);
        
        
        System.out.println("search ===============");
        
        ArrayList<Order> ordersAsList = os.searchOrders(null , 1 , null);
        System.out.println(ordersAsList.toString());
        
        System.out.println("Update ===============");
        os.updateOrder(36, null);
        ordersAsList = os.searchOrders(null , 1 , null);
        
    }
    
}
