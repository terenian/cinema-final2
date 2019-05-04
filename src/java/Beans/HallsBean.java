package Beans;

import javax.inject.Named;

import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;



/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "HallsBean")
@SessionScoped

public class HallsBean implements Serializable {
    
    private List<Hall> hallsList;
    private Hall hall;
                
    public HallsBean() {
        
    }
   
    public List<Hall> getHallsList() throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        hallsList = ServiceInit.hallsService().searchHalls(null,"");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return hallsList;
    }
    
    public Hall getHallByID(int hallID) throws ClassNotFoundException, SQLException, Throwable {
        //List<Ticket> myTickets = null;
        hallsList = ServiceInit.hallsService().searchHalls(hallID,"");
        //TicketsService os = new TicketsService (new DBConnector(new Configuration()));
        //yTickets = os.searchTickets(null, orderID, null);
        return hall;
    }
}



