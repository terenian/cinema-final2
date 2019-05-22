package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;


/**
 *Provides Bean services for Reviews-Related Actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "ReviewsBean")
@SessionScoped
public class ReviewsBean implements Serializable {
    
    private List<Review> reviewsList; 
    private int movieForReview;
    private String review;
    private Movie movie;
    
    /**
     * review Setter
     * @param rev String with the review content
     */
    public void setReview(String rev){this.review=rev;}

    /**
     * review Getter
     * @return a String with the review content
     */
    public String getReview(){return review;}
    
    /**
     * gets the reviewd movie
     * @return  Movie
     */
    public Movie getMovie(){return this.movie;}
    
    /**
     * Default constructor
     */
    public ReviewsBean() {
        
    }
   
    /**
     *
     * @return a List of reviews record
     * @throws SQLException
     */
    public List<Review> getReviewList() throws SQLException {
        reviewsList = ServiceInit.reviewsServiece().searchReviewsbyMovie(movieForReview);
        return reviewsList;
    }
    
    /**
     *
     * @return a page direction, depends if the review inserted successfully
     */
    public String saveReview(){
        try {
            //TODO: replace 1 with real user ID from Session
            System.out.println(review);
            ServiceInit.reviewsServiece().insertReview(review, movieForReview,SessionUtils.getUserId());
            this.clean();
            return "ThanksForReview";
        } catch (SQLException ex) {
            CinemaLogger.log(Level.SEVERE, this.getClass() + ex.getMessage());
            System.out.println("REVIEW INSERTION PROCCESS FAILED");
        }
        return "Error";
    }
    
    /**
     * sets the reviewed movie in the bean class
     * @param movie Movie
     * @return page name navigation to write the review
     * @throws SQLException
     */
    public String setMovieForReview (String movie) throws SQLException{
        this.movieForReview = Integer.parseInt(movie);
        this.getMovieByID(movieForReview);
        return "writeReview";
    }
    
    /**
     * gets a movie from the DB with its name
     * @param movieID 
     * @return page name navigation
     * @throws SQLException
     */
    public String getMovieByID(int movieID) throws SQLException {
        movie = ServiceInit.moviesService().searchMoviesbyID(movieID).get(0);
        movieForReview = movieID;
        this.getReviewList();
        return "showReviews";
    }
    
    /**
     *  getter
     * @return int the reviewed movie
     */
    public int getMovieForReview (){
        return movieForReview;
    }

    /**
     * cleans the bean elements
     */
    public void clean() {
        this.movie=null;
        this.review="";
        this.reviewsList=null;

    }

}



