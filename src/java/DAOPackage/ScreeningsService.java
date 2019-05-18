/*
* This class gives a service of CRUD actions on Screenings Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Screening;
import java.util.Date;


/**
 *
 * @author Eran Z. & Itzik W
 */
public class ScreeningsService {
    
    
    private DBConnector dbConnection;
    
    private final String SCREENING_INSERT = "insert into cinema.screenings (MovieID, HallID, Price, MarkedTickets, Date, Time) values (?,?,?,?,?,?)";
    private final String SCREENING_SEARCH = "select * from cinema.screenings where ScreeningID like (?) and HallID like (?) and MovieID like (?) and Price like (?) and MarkedTicket like (?) and Date like (?) and Time like (?)";
    private final String SCREENING_UPDATE = "update cinema.screenings set Price=COALESCE((?),price) where ScreeningID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    
    public ScreeningsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /*
    * Inserts a new screening
    */
    public Integer insertScreening(Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
        
        //logger.info("insert Screening("+screeningID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement screeningInsertSTM = null;
        screeningInsertSTM = c.prepareStatement(SCREENING_INSERT);
        
        screeningInsertSTM.setInt(1, hall);
        screeningInsertSTM.setInt(2, movie);
        screeningInsertSTM.setInt(3, price);
        screeningInsertSTM.setInt(4, markedTkts);
        screeningInsertSTM.setString(5, date);
        screeningInsertSTM.setString(6, time);
        int result = 0;
        screeningInsertSTM.executeUpdate();
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
    public ArrayList<Screening> searchScreenings(Integer id, Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
        //logger.info("search Screening("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement screeningSearchSTM = null;
        screeningSearchSTM = c.prepareStatement(SCREENING_SEARCH);
        if (id == null) {
            screeningSearchSTM.setString(1, "%");
        }
        else{
            screeningSearchSTM.setInt(1, id);
        }
        if (hall == null) {
            screeningSearchSTM.setString(2, "%");
        }
        else{
            screeningSearchSTM.setInt(2, hall);
        }
        if (movie == null) {
            screeningSearchSTM.setString(3,  "%");
        }
        else{
            screeningSearchSTM.setInt(3, movie);
        }
        if (price == null) {
            screeningSearchSTM.setString(4,  "%");
        }
        else{
            screeningSearchSTM.setInt(4, price);
        }
        if (markedTkts == null) {
            screeningSearchSTM.setString(5,  "%");
        }
        else{
            screeningSearchSTM.setInt(5, markedTkts);
        }
        if (date == null) {
            screeningSearchSTM.setString(6,  "%");
        }
        else{
            screeningSearchSTM.setString(6, date);
        }
        if (time == null) {
            screeningSearchSTM.setString(7,  "%");
        }
        else{
            screeningSearchSTM.setString(7, time);
        }
        
        System.out.println("screeningSearchSTM IS: " + screeningSearchSTM.toString());
        
        ResultSet rs = screeningSearchSTM.executeQuery();
        ArrayList<Screening> list = new ArrayList<Screening> ();
        while(rs.next()){
            Screening screen = new Screening (rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getInt(4), rs.getInt(5), rs.getDate(6),rs.getDate(7));
            list.add(screen);
        }
        return list;
    }
    
    
    public boolean updateScreening(Integer id,Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
        //logger.info("update Screening("+id+","+name+","+description+","+parentId+")");
        if (id == null || hall == null || movie == null || date == null || time == null || price == null || markedTkts == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement screeningUpdateSTM = null;
        screeningUpdateSTM = c.prepareStatement(SCREENING_UPDATE);
                
        //System.out.println("screeningUpdateSTM IS: " + screeningUpdateSTM.toString());
        
        screeningUpdateSTM.setInt(7, id);
        screeningUpdateSTM.setInt(1, hall);
        screeningUpdateSTM.setInt(2, movie);
        screeningUpdateSTM.setInt(3, price);
        screeningUpdateSTM.setInt(4, markedTkts);
        screeningUpdateSTM.setString(5,time);
        screeningUpdateSTM.setString(6, date);
        
        
        
        //System.out.println("screeningUpdateSTM IS: " + screeningUpdateSTM.toString());
        
        Integer i = screeningUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    public boolean deleteScreening(int screeningID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select ScreeningID from tickets where screeningID = (?)");
        prepStat.setInt(1, screeningID);
        
        ResultSet rs = prepStat.executeQuery();
        if(rs.first())
        {
            return false;
        }
        else
        {
            prepStat = c.prepareStatement("delete from screenings where ScreeningID = (?)");
            prepStat.setInt(1, screeningID);
            return (prepStat.executeUpdate()> 0);
        }
    }
    public boolean addScreening(int movieID, int hallID, int price, int marked,Date date,Date time) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = null;
        prepStat = c.prepareStatement("INSERT INTO screenings (MovieID,HallID,Price,MarkedTicket,Date,Time) VALUES ((?),(?),(?),(?),(?),(?))");
        prepStat.setInt(1, movieID);
        prepStat.setInt(2, hallID);
        prepStat.setInt(3, price);
        prepStat.setInt(4, marked);
        prepStat.setDate(5, new java.sql.Date(date.getTime()));
        prepStat.setTime(6, new java.sql.Time(time.getTime()));
        
        return (prepStat.executeUpdate()> 0);
    }
    public Screening searchScreeningByID(int screeningID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select * from screenings where ScreeningID = (?)");
        prepStat.setInt(1, screeningID);
        Screening screening = null;      
        ResultSet rs = prepStat.executeQuery();
        if(rs.first())
        {
            screening = new Screening (rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getInt(4), rs.getInt(5), rs.getDate(6),rs.getDate(7));
        }
        return screening;
    }
    
       
}

