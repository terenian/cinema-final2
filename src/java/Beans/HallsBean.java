package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;

import java.io.Serializable;
import EntitiesLayer.*;
import com.sun.media.jfxmedia.logging.Logger;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



/**
 *Provides Bean services to hall-related actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "HallsBean")
@SessionScoped

public class HallsBean implements Serializable {
    
    private List<Hall> hallsList;
    private Hall newHall;

    /**
     *Defoult empty Constructor
     */
    public HallsBean() {
        this.newHall = new Hall();
    }
   
    /**
     *
     * @return newHall
     */
    public Hall getNewHall() {
        return newHall;
    }
    

    /**
     *
     * @return List<Hall>  of all available halls
     * @throws SQLException
     */
    
    public List<Hall> getHallsList() throws  SQLException {
        hallsList = ServiceInit.hallsService().searchHalls(0,"");
        return hallsList;
    }
    
    /**
     *
     * @param hallID
     * @return Hall of the given ID, Empty List if none found
     * @throws SQLException
     */
    public Hall getHallByID(int hallID) throws  SQLException  {
        hallsList = ServiceInit.hallsService().searchHalls(hallID,"");
        return hallsList.get(0);
    }

    /**
     *Adds a new Hall
     */
    public void addHall ()
    {
        try
        {
            ServiceInit.hallsService().addHall(this.newHall.getHallName(),this.newHall.getHallLength(),this.newHall.getHallWidth());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(e.getMessage()));
            CinemaLogger.log(Level.SEVERE, this.getClass()+e.getMessage());
        }
    }

    /**
     * Deletes Hall by HallID
     * @param hallID
     */
    public void deleteHall(int hallID)
    {
        try{
            if(!ServiceInit.hallsService().deleteHall(hallID))
                      {
                        FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Hall has existing screenings and/or tickets",
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



