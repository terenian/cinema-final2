package Beans;

import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
@Named(value = "ScreeningsBean")
@ManagedBean
@javax.enterprise.context.SessionScoped
public class ScreeningsBean implements Serializable {
    
    /**
     * Creates a new instance of ScreeningsBean
     */
    
    public ScreeningsBean() {
    }
    
    private Hall hall;
    private Movie movie;
    private Screening screening;
    private int newOrderID;
    private int orderPrice;
    private int numberOfDesiredTickets;
    private int numberOfFreeTickets;
    private List<Screening> screeningsListByHallID;
    private List<Screening> screeningsListByMovieID;
    private List<Movie> moviesList;
    private ArrayList<ArrayList<String>> seatsForOrder ;
    private String chosenSeats;
    private Logger logger = ServiceInit.getLogger();
    
    
    public Hall getHall() {return hall;}
    public Movie getMovie() {return movie;}
    public Screening getScreening() {return screening;}
    public int getNewOrderID (){
        return newOrderID;
    }
    
    
    public int getNumberOfFreeTickets() throws SQLException {
        int numberOfTakenSeats = ServiceInit.ticketsService().searchTicket(null, screeningID, null, null).size();
        numberOfFreeTickets = (hall.getHallLength()*hall.getHallWidth())- numberOfTakenSeats;
        return numberOfFreeTickets;
    }
    
    public int getNumberOfDesiredTickets() {return numberOfDesiredTickets;}
    public void setNumberOfDesiredTickets(int num) {
        numberOfDesiredTickets = num;
    }
    
    public void validateFreeTickets (FacesContext context, UIComponent comp,Object value) {
        int numberOfDesiredTicketsString = (Integer) value;
        if (numberOfDesiredTicketsString > numberOfFreeTickets || numberOfDesiredTicketsString < 1) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("Maximum Free Tickets is: " + numberOfFreeTickets);
            context.addMessage(comp.getClientId(context), message);
        }
    }
    
    public int getOrderPrice() {
        if(seatsForOrder!=null){ //it is a MARKED seats tickets
            orderPrice = seatsForOrder.size()*screening.getPrice();
            return orderPrice;
        } else { //its UNMARKED seats tickets
            orderPrice = numberOfDesiredTickets*screening.getPrice();
            return orderPrice;
        }
    }
    
    //contrlo Navigation: check if the screening is reserved-seats or not
    public String checkMarkedSeats (){
        if (screening.getMarkedTicket() == 1){
            return ("chooseSeats");
        }
        return ("insertNumberOfTickets");
        
    }
    
    //Control Navigation: check if seats are chosen
    public String checkChosenSeats (){
        logger.log(Level.INFO, this.getClass() + " chosen seats are: " + seatsForOrder.toString());
        System.out.println(" = = = seatsForOrder are:" + seatsForOrder);
        if (seatsForOrder == null){
            return ("showSeats");
        }
        return ("showSummary");
        
    }
    
    public List<Movie> getMoviesList() { return moviesList;}
    
    public ArrayList<ArrayList<String>> getSeatsForOrder (){return seatsForOrder;}
    
    //public String[] getChosenSeats (){return chosenSeats;}
    public String getChosenSeats (){return chosenSeats;}
    public void setChosenSeats (String stringOfSeats){
        chosenSeats = stringOfSeats;
        if (stringOfSeats.length()>0){
            List<String> seatsList = Arrays.asList(stringOfSeats.split(","));
            //System.out.println("== seatList is: "+ seatsList +"and its length is: " + seatsList.size());
            ArrayList<String> seat = new  ArrayList<String>();
            seatsForOrder = new ArrayList<ArrayList<String>>();
            for (int i=0; i<seatsList.size(); i+=2){
                seat.add(0, seatsList.get(i));
                seat.add(1, seatsList.get(i+1));
                //System.out.println( seat.get(0) + " , " + seat.get(1));
                seatsForOrder.add(seat);
                seat = new  ArrayList<String>();
            }
            numberOfDesiredTickets=seatsForOrder.size();
            logger.info(this.getClass() + " seats for order are: " + seatsForOrder);
            //System.out.println ("seatsForOrde is: " + seatsForOrder)   ;
        }
        else {
            seatsForOrder = null;
            chosenSeats = "0";
        }
    }
    
    
    public List<Screening> getScreeningsListByMovieID() throws SQLException {
        screeningsListByMovieID = ServiceInit.screeningsService().searchScreenings(null,hallID, movieID, null, null,null, null);
        return screeningsListByMovieID;
    }

    public List<Screening> getScreeningsListByHallID() throws SQLException {
        screeningsListByHallID = ServiceInit.screeningsService().searchScreenings(null,hallID, null, null, null,null, null);
        this.createMoviesListfromScreeningList();
        return screeningsListByHallID;
    }
    
    public void createMoviesListfromScreeningList () throws SQLException {
        int movID;
        moviesList = new ArrayList<>();
        for (int i=0; i<screeningsListByHallID.size(); i++){
            movID=screeningsListByHallID.get(i).getMovieID();
            //System.out.println(" movID is: ========= :" + movID);
            logger.info(this.getClass() + "movID is: ========= :" + movID);
            if (moviesList.isEmpty()) {
                moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                logger.info(this.getClass() + " ==Found Movie Is  ========= :" + moviesList.get(0).toString());
                //System.out.println("==Found Movie Is  ========= :" + moviesList.get(0).toString());
            }
            else{
                for (int j=0; j<moviesList.size(); j++){
                    if (moviesList.get(j).getMovieId() == movID ){
                        continue;
                    }
                    moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                    logger.info(this.getClass() + "movie ADDED is: ========= :" + movID);
                    //System.out.println("movie ADDED is: ========= :" + movID);
                }
            }
        }
    }
    
    public void saveOrder(){
        try {
            newOrderID = ServiceInit.orderService().insertOrder(SessionUtils.getUserId(), orderPrice);
            if(seatsForOrder != null){ //insert MARKED tickets
                for (ArrayList<String> seat : seatsForOrder) {
                    ServiceInit.ticketsService().insertOrder(newOrderID, screeningID, Integer.parseInt(seat.get(0)), Integer.parseInt(seat.get(1)));
                }
            } else { //insert UNMARKED tickets
                for (int i=0; i<numberOfDesiredTickets; i++){
                     ServiceInit.ticketsService().insertOrder(newOrderID, screeningID, 0, 0);
                }
            }
        } catch (SQLException ex) {
            logger.severe(this.getClass() + " ORDER INSERTION PROCCESS FAILED" + moviesList.get(0).toString());
            //System.out.println("ORDER INSERTION PROCCESS FAILED");
        }
    }
    
    
    private int movieID;
    public int getMovieID() {return movieID;}
    public void setMovieID(int movie) throws SQLException {
        this.movieID = movie;
        this.movie= ServiceInit.moviesService().searchMoviesbyID(movieID).get(0);
    }
    
    private int screeningID;
    public int getScreeningID() {return screeningID;}
    public void setScreeningID(int scr) throws SQLException {
        this.screeningID = scr;
        this.screening = ServiceInit.screeningsService().searchScreenings(screeningID, null, null, null, null, null, null).get(0);
    }
    
    private int hallID;
    public int getHallID() {return hallID;}
    public void setHallID (int hallToSet) throws SQLException{
        cleanOrder();
        logger.info(this.getClass() + " hall is: ========= :" + hallToSet);
        System.out.println("hall is: ========= :" + hallToSet);
        this.hallID = hallToSet;
        this.hall= ServiceInit.hallsService().searchHalls(hallID, "").get(0);
        this.getScreeningsListByHallID();
        //this.createMoviesListfromScreeningList();
    }
    
    public void cleanOrder()
    {
        this.moviesList = null;
        this.screeningsListByHallID = null;
        this.screeningsListByMovieID = null;
        this.hallID = 0;
        this.movieID = 0;
        this.screeningID = 0;
        this.chosenSeats = "";
        this.screening = null;
        this.hall = null;
        this.movie = null;
        this.seatsForOrder = null;
    }
}



