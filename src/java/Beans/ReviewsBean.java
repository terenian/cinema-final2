package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.RequestScoped;


/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "ReviewsBean")
@SessionScoped
public class ReviewsBean implements Serializable {
    
    private List<Review> reviewsList; 
    private int movieForReview;
    
    private String review;
    public void setReview(String rev){this.review=rev;}
    public String getReview(){return review;}
    
    
    public ReviewsBean() {
        
    }
   
    public List<Review> getReviewList() throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        reviewsList = ServiceInit.reviewsServiece().searchReviewsbyMovie(movieForReview);
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return reviewsList;
    }
    
    public String saveReview(){
        try {
            //TODO: replace 1 with real user ID from Session
            System.out.println(review);
            ServiceInit.reviewsServiece().insertReview(review, movieForReview, 1);
            return "ThanksForReview";
        } catch (SQLException ex) {
            //Logger.getLogger(ScreeningsBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("REVIEW INSERTION PROCCESS FAILED");
        }
        return "Error";
    }
    
    public String setMovieForReview (String movie){
        this.movieForReview = Integer.parseInt(movie);
        return "WriteReview";
    }
    public int getMovieForReview (){
        return movieForReview;
    }
    
    
}



