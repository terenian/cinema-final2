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
import java.util.logging.Level;

/**
 * This class gives a service of CRUD actions on Tickets Table.
 * @author Eran Z. & Itzik W
 */
public class TicketsService {
    
    
    private DBConnector dbConnection;
    
    private final String TICKET_INSERT = "insert into cinema.tickets (OrderID, ScreeningID, RowNum, ColumnNum) values (?,?,?,?)";
    private final String TICKET_SEARCH = "select * from cinema.tickets where TicketID like (?) and OrderID like (?) and ScreeningID like (?) and RowNum like (?) and ColumnNum like (?) ORDER BY RowNum, ColumnNum";
    //Update is used only for markin a ticket as Used
    private final String TICKET_UPDATE = "update cinema.tickets set Used=1 where OrderID like (?) and RowNum like (?) and ColumnNum like (?)";
    private final String TICKET_UPDATE_BY_TICKETID = "update cinema.tickets set Used=1 where TicketID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    /**
     *
     * @param dbConnection
     */
    public TicketsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /**
     * Inserts a new ticket
     * @param order
     * @param screening
     * @param row
     * @param column
     * @return inserted TicketID
     * @throws SQLException
     */
    
    public Integer insertOrder(Integer order, Integer screening, Integer row, Integer column)throws SQLException{
        
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketInsertSTM = null;
        ticketInsertSTM = c.prepareStatement(TICKET_INSERT,Statement.RETURN_GENERATED_KEYS);
        
        ticketInsertSTM.setInt(1, order);
        ticketInsertSTM.setInt(2, screening);
        ticketInsertSTM.setInt(3, row);
        ticketInsertSTM.setInt(4, column);
        int result = ticketInsertSTM.executeUpdate();
        if (result == 0) {
            CinemaLogger.log(Level.SEVERE, this.getClass() + "== Creating ticket failed, no rows affected.");
            throw new SQLException("== Creating ticket failed, no rows affected.");
        }
        try (ResultSet generatedKeys = ticketInsertSTM.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (generatedKeys.getInt(1));
            }
            else {
                CinemaLogger.log(Level.SEVERE, this.getClass() + "===Creating ticket failed, no ID obtained");
                throw new SQLException("==Creating ticket failed, no ID obtained.");
            }
        }

    }
    
    /**
     * List of Tickets
     * @param tid
     * @param order
     * @param screening
     * @param row
     * @param column
     * @return
     * @throws SQLException
     */
    
    public ArrayList<Ticket> searchTicket(Integer tid, Integer order, Integer screening, Integer row, Integer column)
            throws SQLException{
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketSearchSTM = null;
        ticketSearchSTM = c.prepareStatement(TICKET_SEARCH);
        if (tid == null) {
            ticketSearchSTM.setString(1, "%");
        } 
        else{
            ticketSearchSTM.setInt(1, tid);
        }
        if (order == null) {
            ticketSearchSTM.setString(2, "%");
        }
        else{
            ticketSearchSTM.setInt(2, order);
        }
        if (screening == null) {
            ticketSearchSTM.setString(3, "%");
        }
        else{
            ticketSearchSTM.setInt(3, screening);
        }
        if (row == null) {
            ticketSearchSTM.setString(4,  "%");
        }
        else{
            ticketSearchSTM.setInt(4, row);
        }
        if (column == null) {
            ticketSearchSTM.setString(5,  "%");
        }
        else{
            ticketSearchSTM.setInt(5, column);
        }
        
        CinemaLogger.log(Level.INFO, this.getClass() + "ticketSearchSTM is" + ticketSearchSTM.toString());
        
        ResultSet rs = ticketSearchSTM.executeQuery();
        ArrayList<Ticket> list = new ArrayList<Ticket> ();
        while(rs.next()){
            Ticket tkt = new Ticket(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6));
            list.add(tkt);
        }
        return list;
    }

    /**
     *
     * @param orderID
     * @return int with screening id by Order ID, 0 if none found
     * @throws SQLException
     */
    public int getScreeningIDByOrderID(int orderID)throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select ScreeningID from tickets where OrderID = (?) group by ScreeningID");
        prepStat.setInt(1, orderID);
        
        ResultSet rs = prepStat.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        } else {
            return 0;
        }
        
    }
    
    /**
     * 
     * @param order
     * @param row
     * @param col
     * @return true if update succeed
     * @throws SQLException
     */
    public boolean updateTicket(Integer order, Integer row, Integer col)
            throws SQLException{
        if (order == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketUpdateSTM = null;
        ticketUpdateSTM = c.prepareStatement(TICKET_UPDATE);
        
        ticketUpdateSTM.setInt(1, order);
        ticketUpdateSTM.setInt(2, row);
        ticketUpdateSTM.setInt(3, col);
                      
        CinemaLogger.log(Level.INFO, this.getClass() + "ticketUpdateSTM is" + ticketUpdateSTM.toString());
        
        Integer i = ticketUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    
    /**
     *
     * @param ticketID
     * @return true if update succeed
     * @throws SQLException
     */
    public boolean updateTicketByID(Integer ticketID)throws SQLException{
        if (ticketID == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement ticketUpdateSTM = null;
        ticketUpdateSTM = c.prepareStatement(TICKET_UPDATE_BY_TICKETID);
        
        ticketUpdateSTM.setInt(1, ticketID);
         
        CinemaLogger.log(Level.INFO, this.getClass() + "ticketUpdateSTM is" + ticketUpdateSTM.toString());
        
        Integer i = ticketUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    
}
