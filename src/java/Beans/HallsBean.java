package Beans;

import javax.inject.Named;

import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;



/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "HallsBean")
@SessionScoped

public class HallsBean implements Serializable {
    
    private List<Hall> hallsList;
    private Hall newHall;

    
                
    public HallsBean() {
        this.newHall = new Hall();
    }
   
    public Hall getNewHall() {
        return newHall;
    }
    public List<Hall> getHallsList() throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        hallsList = ServiceInit.hallsService().searchHalls(0,"");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return hallsList;
    }
    
    public Hall getHallByID(int hallID) throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        hallsList = ServiceInit.hallsService().searchHalls(hallID,"");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return hallsList.get(0);
    }
    public void addHall ()
    {
        try
        {
            ServiceInit.hallsService().addHall(this.newHall.getHallName(),this.newHall.getHallLength(),this.newHall.getHallWidth());
        }
        catch(Exception e)
        {
            FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(e.getMessage()));
        }
    }
    public void deleteHall(int hallID)
    {
        try{
            if(!ServiceInit.hallsService().deleteHall(hallID))
                      {
                        FacesContext.getCurrentInstance().addMessage(
                                              null,
                                              new FacesMessage(FacesMessage.SEVERITY_WARN,
                                                              "This Hall has existing screenings and/or tickets",
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



