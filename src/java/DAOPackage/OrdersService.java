/*
* This class gives a service of CRUD actions on Orders Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Order;


/**
 *
 * @author Eran Z. & Itzik W
 */
public class OrdersService {
    
    
    private DBConnector dbConnection;
    
    private final String ORDER_INSERT = "insert into cinema.orders (UserID, price) values (?,?)";
    private final String ORDER_SEARCH = "select * from cinema.orders where OrderID like (?) and UserID like (?) and Price like (?)";
    private final String ORDER_UPDATE = "update cinema.orders set Price=COALESCE((?),price) where OrderID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    
    public OrdersService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /*
    * Inserts a new order
    */
    public Integer insertOrder(Integer user, Integer price)
            throws SQLException{
        
        //logger.info("insert Order("+orderID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement orderInsertSTM = null;
        orderInsertSTM = c.prepareStatement(ORDER_INSERT);
        
        orderInsertSTM.setInt(1, user);
        orderInsertSTM.setInt(2, price);
        int result = 0;
        orderInsertSTM.executeUpdate();
        if (result >= 0) {
            System.out.println("SuccessInsert");
        }
        else{
            System.out.println("UnSeccessed insert!");
        }
        return result;
    }
    
    /*
    * Search for rows
    */
    public ArrayList<Order> searchOrders(Integer id, Integer user, Integer price)
            throws SQLException{
        //logger.info("search Order("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement orderSearchSTM = null;
        orderSearchSTM = c.prepareStatement(ORDER_SEARCH);
        if (id == null) {
            orderSearchSTM.setString(1, "%");
        }
        else{
            orderSearchSTM.setInt(1, id);
        }
        if (user == null) {
            orderSearchSTM.setString(2, "%");
        }
        else{
            orderSearchSTM.setInt(2, user);
        }
        if (price == null) {
            orderSearchSTM.setString(3,  "%");
        }
        else{
            orderSearchSTM.setInt(3, price);
        }
        
        System.out.println("orderSearchSTM IS: " + orderSearchSTM.toString());
        
        ResultSet rs = orderSearchSTM.executeQuery();
        ArrayList<Order> list = new ArrayList<Order> ();
        while(rs.next()){
            Order ord = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            list.add(ord);
        }
        return list;
    }
    
    
    public boolean updateOrder(Integer id, Integer price)
            throws SQLException{
        //logger.info("update Order("+id+","+name+","+description+","+parentId+")");
        if (id == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement orderUpdateSTM = null;
        orderUpdateSTM = c.prepareStatement(ORDER_UPDATE);
                
        //System.out.println("orderUpdateSTM IS: " + orderUpdateSTM.toString());
        if (price == null) {
            orderUpdateSTM.setNull(1, java.sql.Types.INTEGER);
        }
        else
        {
            orderUpdateSTM.setInt(1,price);
        }
        orderUpdateSTM.setInt(2, id);
        
        //System.out.println("orderUpdateSTM IS: " + orderUpdateSTM.toString());
        
        Integer i = orderUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    
}
