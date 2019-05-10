package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;


/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "MoviesBean")
@SessionScoped
public class MoviesBean implements Serializable {
    
    private List<Movie> movieList; 
    private int movieForReview;
    public MoviesBean() {
        
    }
   
    public List<Movie> getMovieList() throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        movieList = ServiceInit.moviesService().searchMoviesbyName("");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return movieList;
    }
    
    public void setMovieForReview (int movie){
        this.movieForReview = movie;
    }
    
    
}



