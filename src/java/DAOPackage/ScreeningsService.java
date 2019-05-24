package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Screening;
import java.util.Date;
import java.util.logging.Level;


/**
 *This class gives a service of CRUD actions on Screenings Table.
 * @author Eran Z. & Itzik W
 */
public class ScreeningsService {
    
    
    private DBConnector dbConnection;
    
    private final String SCREENING_INSERT = "insert into cinema.screenings (MovieID, HallID, Price, MarkedTickets, Date, Time) values (?,?,?,?,?,?)";
    private final String SCREENING_SEARCH = "select * from cinema.screenings where ScreeningID like (?) and HallID like (?) and MovieID like (?) and Price like (?) and MarkedTicket like (?) and Date like (?) and Time like (?)";
    private final String SCREENING_UPDATE = "update cinema.screenings set Price=COALESCE((?),price) where ScreeningID like (?)";
    
    /**
     *
     * @param dbConnection
     */
    public ScreeningsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /**
     *Inserts a new screening
     * @param hall
     * @param movie
     * @param price
     * @param markedTkts
     * @param date
     * @param time
     * @return id of inserted screening
     * @throws SQLException
     */
    
    public Integer insertScreening(Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
        
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
        CinemaLogger.log(Level.INFO, this.getClass() + "screeningInsertSTM is" + screeningInsertSTM.toString());
        if (result >= 0) {
           CinemaLogger.log(Level.INFO, this.getClass() +"SuccessInsert");
        }
        else{
            CinemaLogger.log(Level.SEVERE, this.getClass() +"UnSeccessed insert!");
        }
        return result;
    }
    

    /**
     *Search for Screenings
     * @param id
     * @param hall
     * @param movie
     * @param price
     * @param markedTkts
     * @param date
     * @param time
     * @return list of screenings
     * @throws SQLException
     */
    
    public ArrayList<Screening> searchScreenings(Integer id, Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
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
        
        CinemaLogger.log(Level.INFO, this.getClass() +"screeningSearchSTM IS: " + screeningSearchSTM.toString());
        
        ResultSet rs = screeningSearchSTM.executeQuery();
        ArrayList<Screening> list = new ArrayList<Screening> ();
        while(rs.next()){
            Screening screen = new Screening(rs.getInt(1), rs.getInt(3), rs.getInt(2),rs.getInt(4), rs.getInt(5), rs.getDate(6),rs.getDate(7));
            list.add(screen);
        }
        return list;
    }
    
    /**
     *
     * @param id
     * @param hall
     * @param movie
     * @param price
     * @param markedTkts
     * @param date
     * @param time
     * @return true if update succeed
     * @throws SQLException
     */
    public boolean updateScreening(Integer id,Integer hall, Integer movie, Integer price, Integer markedTkts, String date, String time)
            throws SQLException{
        if (id == null || hall == null || movie == null || date == null || time == null || price == null || markedTkts == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement screeningUpdateSTM = null;
        screeningUpdateSTM = c.prepareStatement(SCREENING_UPDATE);
                        
        screeningUpdateSTM.setInt(7, id);
        screeningUpdateSTM.setInt(1, hall);
        screeningUpdateSTM.setInt(2, movie);
        screeningUpdateSTM.setInt(3, price);
        screeningUpdateSTM.setInt(4, markedTkts);
        screeningUpdateSTM.setString(5,time);
        screeningUpdateSTM.setString(6, date);
        
        
        
       CinemaLogger.log(Level.INFO, this.getClass()  + "screeningUpdateSTM is: " + screeningUpdateSTM.toString());
        
        Integer i = screeningUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }

    /**
     *
     * @param screeningID
     * @return true if deletion succeed
     * @throws SQLException
     */
    public boolean deleteScreening(int screeningID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select ScreeningID from tickets where screeningID = (?)");
        prepStat.setInt(1, screeningID);
        CinemaLogger.log(Level.INFO, this.getClass()  + "screening deletion command: " + prepStat.toString());
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

    /**
     *
     * @param movieID
     * @param hallID
     * @param price
     * @param marked
     * @param date
     * @param time
     * @return true if add succeed
     * @throws SQLException
     */
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

    /**
     * 
     * @param screeningID
     * @return Screening record
     * @throws SQLException
     */
    public Screening searchScreeningByID(int screeningID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select * from screenings where ScreeningID = (?)");
        prepStat.setInt(1, screeningID);
        Screening screening = null;      
        ResultSet rs = prepStat.executeQuery();
        CinemaLogger.log(Level.INFO, this.getClass() + "searchScreening command is" + prepStat.toString());
        if(rs.first())
        {
            screening = new Screening (rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getInt(4), rs.getInt(5), rs.getDate(6),rs.getDate(7));
        }
        return screening;
    }
    
       
}

