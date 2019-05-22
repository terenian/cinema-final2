package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.SessionUtils;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 * Provides Bean services for Movie-Related Actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "MoviesBean")
@SessionScoped
public class MoviesBean implements Serializable {
    
    private List<Movie> movieList; 
    private Movie newMovie;

    /**
     *
     * @return new Movie
     */
    public Movie getNewMovie() {
        return newMovie;
    }
    private int movieForReview;
    
    /**
     * 
     */
    public MoviesBean() {
        newMovie = new Movie();
    }
   
    /**
     *
     * @return
     * @throws SQLException
     */
    public List<Movie> getMovieList() throws  SQLException {
        movieList = ServiceInit.moviesService().searchMoviesbyName("");
        return movieList;
    }
    
    /**
     * Sets a movie for review for the current page
     * @param movie
     */
    public void setMovieForReview (int movie){
        this.movieForReview = movie;
    }

    /**
     *Adds a new Movie
     */
    public void addMovie ()
    {
        try
        {
            ServiceInit.moviesService().addMovie(this.newMovie.getMovieName(),this.newMovie.getMovieLength());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass()+e.getMessage());
        }
    }

    /**
     * Deletes a Movie by MovieID
     * @param movieID
     */
    public void deleteMovie(int movieID)
    {
        try{
            if(!ServiceInit.moviesService().deleteMovie(movieID))
                      {
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Movie has existing screening and/or tickets",
                                                              "Please delete all of them before removing it")); 
                      }
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass()+e.getMessage());
        }
    }
     

    
    
}



