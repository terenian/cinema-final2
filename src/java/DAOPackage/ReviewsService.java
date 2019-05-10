/*
* This class gives a service of CRUD actions on Reviews Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Review;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 *
 * @author Eran Z. & Itzik W
 */
public class ReviewsService {
    
    
    private DBConnector dbConnection;
    
    private final String REVIEW_INSERT = "insert into cinema.reviews (MovieID, ReviewDate, Review, UserID) values (?,?,?,?)";
    //Changed by Eran 9.4.19 private final String REVIEW_SEARCH = "select * from cinema.reviews where ReviewID like (?) and ReviewName like (?) and ReviewLength like (?)";
    private final String REVIEW_SEARCH = "select * from cinema.reviews where MovieID like (?) and UserID like (?)";
    
    //private final Logger logger = ServiceManager.getLogger();
    
    
    public ReviewsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /*
    * Inserts a new review
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
            System.out.println("SuccessInsert");
        }
        else{
            System.out.println("UnSeccessed insert!");
        }
        return result;
    }
    
    /*
    * Search for review
    */
    public ArrayList<Review> searchReviewsbyMovie(Integer movie)
            throws SQLException{
        //logger.info("search Review("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement reviewSearchSTM = null;
        reviewSearchSTM = c.prepareStatement(REVIEW_SEARCH);
        if (movie== null) {
            reviewSearchSTM.setString(1, "%");
        }
        else{
            reviewSearchSTM.setInt(1, movie);
        }
        //TODO: change "1" to CurrentUSERID
        reviewSearchSTM.setInt(2, 1);
        
        
        System.out.println("reviewSearchSTM IS: " + reviewSearchSTM.toString());
        
        ResultSet rs = reviewSearchSTM.executeQuery();
        ArrayList<Review> list = new ArrayList<Review> ();
        while(rs.next()){
            Review rev = new Review(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getInt(5));
            list.add(rev);
        }
        return list;
    }
   
   
    
}
