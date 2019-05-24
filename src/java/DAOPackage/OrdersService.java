
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Order;
import java.sql.Statement;
import java.util.logging.Level;


/**
 *This class gives a service of CRUD actions on Orders Table.
 * @author Eran Z. & Itzik W
 */
public class OrdersService {
    
    
    private DBConnector dbConnection;
    
    private final String ORDER_INSERT = "insert into cinema.orders (UserID, price) values (?,?)";
    private final String ORDER_SEARCH = "select * from cinema.orders where OrderID like (?) and UserID like (?) and Price like (?)";
    private final String ORDER_UPDATE = "update cinema.orders set Price=COALESCE((?),price) where OrderID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    /**
     *
     * @param dbConnection
     */
    public OrdersService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    

    /**
     *Inserts a new order
     * @param userID
     * @param price
     * @return int of inserted order
     * @throws SQLException
     */
    
    public Integer insertOrder(int userID, Integer price)
            throws SQLException{
        
        //logger.info("insert Order("+orderID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement orderInsertSTM = null;
        orderInsertSTM = c.prepareStatement(ORDER_INSERT, Statement.RETURN_GENERATED_KEYS);
        
        orderInsertSTM.setInt(1, userID);
        orderInsertSTM.setInt(2, price);
        int result = orderInsertSTM.executeUpdate();
        if (result == 0) {
            CinemaLogger.log(Level.SEVERE, this.getClass() + "Order Insertion Failed");
            throw new SQLException("== Creating Order failed, no rows affected.");
        }
        try (ResultSet generatedKeys = orderInsertSTM.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getInt(1));
            }
            else {
                CinemaLogger.log(Level.SEVERE, this.getClass() + "Order Insertion Failed");
                throw new SQLException("==Creating Order failed, no ID obtained.");
            }
        }
    }
    

    /**
     *Search for rows
     * @param id
     * @param user
     * @param price
     * @return array list of orders
     * @throws SQLException
     */
    
    public ArrayList<Order> searchOrders(Integer id, Integer user, Integer price)
            throws SQLException{
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
        
        CinemaLogger.log(Level.INFO, this.getClass() + " orderSearchSTM is: " +orderSearchSTM.toString());
        
        ResultSet rs = orderSearchSTM.executeQuery();
        ArrayList<Order> list = new ArrayList<Order> ();
        while(rs.next()){
            Order ord = new Order(rs.getInt(1), rs.getInt(2), rs.getInt(3));
            list.add(ord);
        }
        return list;
    }
    
    /**
     * update an order
     * @param id
     * @param price
     * @return true if update succeed
     * @throws SQLException
     */
    public boolean updateOrder(Integer id, Integer price) throws SQLException{
        if (id == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement orderUpdateSTM = null;
        orderUpdateSTM = c.prepareStatement(ORDER_UPDATE);
                
        if (price == null) {
            orderUpdateSTM.setNull(1, java.sql.Types.INTEGER);
        }
        else
        {
            orderUpdateSTM.setInt(1,price);
        }
        orderUpdateSTM.setInt(2, id);
        
        CinemaLogger.log(Level.INFO, this.getClass() + " orderUpdateSTM is: " +orderUpdateSTM.toString());
        
        Integer i = orderUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param orderID
     * @return true if deletion succeed
     * @throws SQLException
     */
    public boolean deleteOrder(int orderID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("delete from tickets where OrderID = (?)");
        prepStat.setInt(1, orderID);
        if (prepStat.executeUpdate() == 0)
        {
            return false;
        }
        prepStat = c.prepareStatement("delete from orders where OrderID = (?)");
        CinemaLogger.log(Level.INFO, this.getClass() + " deletion command is : " +prepStat.toString());
        prepStat.setInt(1, orderID);
        return (prepStat.executeUpdate()> 0);
        
    }
    
}
