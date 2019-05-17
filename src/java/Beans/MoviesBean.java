package Beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import java.sql.SQLException;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "MoviesBean")
@SessionScoped
public class MoviesBean implements Serializable {
    
    private List<Movie> movieList; 
    private Movie newMovie;

    public Movie getNewMovie() {
        return newMovie;
    }
    private int movieForReview;
    
    public MoviesBean() {
        newMovie = new Movie();
    }
   
    public List<Movie> getMovieList() throws  SQLException {
        //List<Ticket> myTickets = null;
        movieList = ServiceInit.moviesService().searchMoviesbyName("");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return movieList;
    }
    
    public void setMovieForReview (int movie){
        this.movieForReview = movie;
    }
    public void addMovie ()
    {
        try
        {
            ServiceInit.moviesService().addMovie(this.newMovie.getMovieName(),this.newMovie.getMovieLength());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(e.getMessage()));
        }
    }
    public void deleteMovie(int movieID)
    {
        try{
            if(!ServiceInit.moviesService().deleteMovie(movieID))
                      {
                        FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Movie has existing screening and/or tickets",
                                                              "Please delete all of them before removing it")); 
                      }
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(e.getMessage()));
        }
    }
     

    
    
}



