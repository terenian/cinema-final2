package DAOPackage;

import Beans.ServiceInit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import EntitiesLayer.Review;
import GeneralWeb.SessionUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;



/**
 * This class gives a service of CRUD actions on Reviews Table.
 * @author Eran Z. & Itzik W
 */
public class ReviewsService {
    
    
    private DBConnector dbConnection;
    
    private final String REVIEW_INSERT = "insert into cinema.reviews (MovieID, ReviewDate, Review, UserID) values (?,?,?,?)";
    private final String REVIEW_SEARCH = "select * from cinema.reviews where MovieID like (?) and UserID like (?) ORDER BY ReviewDate DESC, ReviewID DESC";
    
    
    /**
     * Default constructor
     * @param dbConnection
     */
    public ReviewsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }


    /**
     *nserts a new review
     * @param review
     * @param movieID
     * @param userID
     * @return int of inserted review
     * @throws SQLException
     */
    
    public Integer insertReview(String review, Integer movieID, Integer userID)
            throws SQLException{
        
        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        
        //logger.info("insert Review("+reviewID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement reviewInsertSTM = null;
        reviewInsertSTM = c.prepareStatement(REVIEW_INSERT);
        
        reviewInsertSTM.setInt(1, movieID);
        reviewInsertSTM.setString(2, modifiedDate);
        reviewInsertSTM.setString(3, review);
        reviewInsertSTM.setInt(4, userID);
               
        int result =  reviewInsertSTM.executeUpdate();
        if (result >= 0) {
            CinemaLogger.log(Level.INFO, this.getClass() + "Succeess insert");
        }
        else{
            CinemaLogger.log(Level.SEVERE, this.getClass() + "Faild to insert");
        }
        return result;
    }
    
    /*
    * Search for review
    */

    /**
     * @param movie
     * @return list of reviews by movieID
     * @throws SQLException
     */
    
    public ArrayList<Review> searchReviewsbyMovie(Integer movie)
            throws SQLException{
        Connection c = dbConnection.getConnection();
        PreparedStatement reviewSearchSTM = null;
        reviewSearchSTM = c.prepareStatement(REVIEW_SEARCH);
        if (movie== null) {
            reviewSearchSTM.setString(1, "%");
        }
        else{
            reviewSearchSTM.setInt(1, movie);
        }
        reviewSearchSTM.setInt(2, SessionUtils.getUserId());
        
        CinemaLogger.log(Level.INFO, this.getClass() + "reviewSearchSTM is" + reviewSearchSTM.toString());
        
        ResultSet rs = reviewSearchSTM.executeQuery();
        ArrayList<Review> list = new ArrayList<Review> ();
        while(rs.next()){
            Review rev = new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            list.add(rev);
        }
        return list;
    }
   
   
    
}
