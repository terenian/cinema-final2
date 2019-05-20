package Beans;

import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;


/**
 *
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
   
    public Screening getNewScreening() {return newScreening;}
   
    
    
    public List<Screening> getScreeningsList() throws SQLException {
        screeningsList = ServiceInit.screeningsService().searchScreenings(null,null, null, null, null,null, null);
        return screeningsList;
    }
    public List<Movie> getMoviesList() throws SQLException {
        movieList = ServiceInit.moviesService().searchMoviesbyName("");
        return movieList;
    }
    public List<Hall> getHallsList() throws SQLException {
        hallList = ServiceInit.hallsService().searchHalls(0, "");
        return hallList;
    }
    public void addScreening()
    {
        try
        {
           
            ServiceInit.screeningsService().addScreening(newScreening.getMovieID(), newScreening.getHallID(), newScreening.getPrice(), newScreening.getMarkedTicket()
                    , newScreening.getDate(),newScreening.getTime());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(e.getMessage()));
        }
    }
    public void deleteScreening(int screeningID)
    {
        try{
            if(!ServiceInit.screeningsService().deleteScreening(screeningID))
                      {
                        FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Screening has existing tickets sold to it",
                                                              "you can't delete it")); 
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


