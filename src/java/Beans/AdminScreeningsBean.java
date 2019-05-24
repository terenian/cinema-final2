package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 * Provides Bean services to all Admin page actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "AdminScreeningsBean")
@ManagedBean
@javax.enterprise.context.SessionScoped
public class AdminScreeningsBean implements Serializable {
    
    /**
     * Creates a new instance of ScreeningsBean
     */
    
    public AdminScreeningsBean() {
        this.newScreening = new Screening();
    }
    
    private Screening newScreening; 
    private List<Screening> screeningsList;
    private List<Hall> hallList;
    private List<Movie> movieList;
   
    /**
     * new Screening setter. Generates a new screening in the Admin Page
     * @return Screening
     */
    public Screening getNewScreening() {return newScreening;}
   
    /**
     * gets all the screening list
     * @return list of All Screenings
     * @throws SQLException
     */
    public List<Screening> getScreeningsList() throws SQLException {
        screeningsList = ServiceInit.screeningsService().searchScreenings(null,null, null, null, null,null, null);
        movieList = ServiceInit.moviesService().searchMoviesbyName("");
        hallList = ServiceInit.hallsService().searchHalls(0, "");
        return screeningsList;
    }

    /**
     * MoviesList getter
     * @return List<Movie> 
     * @throws SQLException
     */
    public List<Movie> getMoviesList() throws SQLException {
        return movieList;
    }

    /**
     * Halls List getter
     * @return List<Hall>
     * @throws SQLException
     */
    public List<Hall> getHallsList() throws SQLException {
        return hallList;
    }

    /**
     *MoiveID getter
     * @param movieID
     * @return Movie 
     */
    public Movie getMovieByMovieID(int movieID)
    {
        for (int i = 0; i < this.movieList.size(); i++) 
        {
            if(this.movieList.get(i).getMovieId() == movieID)
                return this.movieList.get(i);
        }
        return new Movie();
    }

    /**
     * gets a hall by its ID
     * @param hallID
     * @return Hall
     */
    public Hall getHallByHallID(int hallID)
    {
        for (int i = 0; i < this.hallList.size(); i++) 
        {
            if(this.hallList.get(i).getHallID()== hallID)
                return this.hallList.get(i);
        }
        return new Hall();
    }

    /**
     * Adds a Screening 
     */
    public void addScreening()
    {
        try
        {
            ServiceInit.screeningsService().addScreening(newScreening.getMovieID(), newScreening.getHallID(), 
                    newScreening.getPrice(), newScreening.getMarkedTicket(),
                    newScreening.getDate(),newScreening.getTime());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass() + e.getMessage());
        }
    }

    /**
     * Deletes a screening by its id
     * @param screeningID
     */
    public void deleteScreening(int screeningID)
    {
        try{
            if(!ServiceInit.screeningsService().deleteScreening(screeningID))
                      {
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Screening has existing tickets sold to it",
                                                              "you can't delete it")); 
                      }
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass() + e.getMessage());
        }
    }


}



