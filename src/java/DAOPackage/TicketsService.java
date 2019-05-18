/*
* This class gives a service of CRUD actions on Tickets Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Order;
import EntitiesLayer.Ticket;
import java.sql.Statement;

/**
 *
 * @author Eran Z. & Itzik W
 */
public class TicketsService {
    
    
    private DBConnector dbConnection;
    
    private final String TICKET_INSERT = "insert into cinema.tickets (OrderID, ScreeningID, RowNum, ColumnNum) values (?,?,?,?)";
    private final String TICKET_SEARCH = "select * from cinema.tickets where OrderID like (?) and ScreeningID like (?) and RowNum like (?) and ColumnNum like (?) ORDER BY RowNum, ColumnNum";
    //Update is used only for markin a ticket as Used
    private final String TICKET_UPDATE = "update cinema.tickets set Used= 1 where OrderID like (?) and RowNum like (?) and ColumnNum like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    
    public TicketsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /*
    * Inserts a new ticket
    */
    public Integer insertOrder(Integer order, Integer screening, Integer row, Integer column)
            throws SQLException{
        
        //logger.info("insert Order("+orderID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketInsertSTM = null;
        ticketInsertSTM = c.prepareStatement(TICKET_INSERT,
                                      Statement.RETURN_GENERATED_KEYS);
        
        ticketInsertSTM.setInt(1, order);
        ticketInsertSTM.setInt(2, screening);
        ticketInsertSTM.setInt(3, row);
        ticketInsertSTM.setInt(4, column);
        int result = ticketInsertSTM.executeUpdate();
        if (result == 0) {
            throw new SQLException("== Creating ticket failed, no rows affected.");
        }
        try (ResultSet generatedKeys = ticketInsertSTM.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getInt(1));
            }
            else {
                throw new SQLException("==Creating ticket failed, no ID obtained.");
            }
        }

    }
    
    /*
    * Search for rows
    */
    public ArrayList<Ticket> searchTicket(Integer order, Integer screening, Integer row, Integer column)
            throws SQLException{
        //logger.info("search Order("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketSearchSTM = null;
        ticketSearchSTM = c.prepareStatement(TICKET_SEARCH);
        if (order == null) {
            ticketSearchSTM.setString(1, "%");
        }
        else{
            ticketSearchSTM.setInt(1, order);
        }
        if (screening == null) {
            ticketSearchSTM.setString(2, "%");
        }
        else{
            ticketSearchSTM.setInt(2, screening);
        }
        if (row == null) {
            ticketSearchSTM.setString(3,  "%");
        }
        else{
            ticketSearchSTM.setInt(3, row);
        }
        if (column == null) {
            ticketSearchSTM.setString(4,  "%");
        }
        else{
            ticketSearchSTM.setInt(4, column);
        }
        
        System.out.println("ticketSearchSTM IS: " + ticketSearchSTM.toString());
        
        ResultSet rs = ticketSearchSTM.executeQuery();
        ArrayList<Ticket> list = new ArrayList<Ticket> ();
        while(rs.next()){
            Ticket tkt = new Ticket(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4));
            list.add(tkt);
        }
        return list;
    }
    public int getScreeningIDByOrderID(int orderID)throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select ScreeningID from tickets where OrderID = (?) group by ScreeningID");
        prepStat.setInt(1, orderID);
        
        ResultSet rs = prepStat.executeQuery();
        if (rs.first())
            return rs.getInt(1);
        else
            return 0;
    }

    public boolean updateTicket(Integer order, Integer row, Integer col)
            throws SQLException{
        //logger.info("update Order("+id+","+name+","+description+","+parentId+")");
        if (order == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketUpdateSTM = null;
        ticketUpdateSTM = c.prepareStatement(TICKET_UPDATE);
        
        ticketUpdateSTM.setInt(1, order);
        ticketUpdateSTM.setInt(2, row);
        ticketUpdateSTM.setInt(3, col);
                      
        System.out.println("ticketUpdateSTM IS: " + ticketUpdateSTM.toString());
        
        Integer i = ticketUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
  
    
}
