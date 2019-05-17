package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import java.sql.SQLException;
import java.util.List;


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
    private Movie movie;
    
    public void setReview(String rev){this.review=rev;}
    public String getReview(){return review;}
    
    public Movie getMovie(){return this.movie;}
    
    public ReviewsBean() {
        
    }
   
    public List<Review> getReviewList() throws SQLException {
        reviewsList = ServiceInit.reviewsServiece().searchReviewsbyMovie(movieForReview);
        return reviewsList;
    }
    
    public String saveReview(){
        try {
            //TODO: replace 1 with real user ID from Session
            System.out.println(review);
            ServiceInit.reviewsServiece().insertReview(review, movieForReview,SessionUtils.getUserId());
            this.clean();
            return "ThanksForReview";
        } catch (SQLException ex) {
            //Logger.getLogger(ScreeningsBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("REVIEW INSERTION PROCCESS FAILED");
        }
        return "Error";
    }
    
    public String setMovieForReview (String movie) throws SQLException{
        this.movieForReview = Integer.parseInt(movie);
        this.getMovieByID(movieForReview);
        return "writeReview";
    }
    
    public String getMovieByID(int movieID) throws SQLException {
        movie = ServiceInit.moviesService().searchMoviesbyID(movieID).get(0);
        movieForReview = movieID;
        this.getReviewList();
        return "showReviews";
    }
    
    public int getMovieForReview (){
        return movieForReview;
    }

    public void clean() {
        this.movie=null;
        this.review="";
        this.reviewsList=null;

    }

}



