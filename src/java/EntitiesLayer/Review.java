package EntitiesLayer;

/**
 *This class represents a Review item in the Reviews Table.
 * @author Eran Z. & Itzik W.
 */
public class Review {
    
    
    private Integer reviewID;
    private Integer movieID;
    private String review;
    private String date;
    private Integer userID;

    
    public Review(Integer id, Integer movie, String review, String date, Integer user){
        this.reviewID = id;
        this.movieID = movie;
        this.date = date;
        this.review = review;
        this.userID = user;
    }
    
    public Integer getReviewID() {
        return reviewID;
    }
    
    public Integer getMovieID() {
        return movieID;
    }
     public Integer getUserID() {
        return userID;
    }
    
    public String getDate() {
        return date;
    }
    public String getReview() {
        return review;
    }
    
  
    public String toString(){
        return  "[" + reviewID+" , "+
                movieID+" , "+
                review+" , "+
                userID+" , "+
                date + "]";
    }
   
    
}

