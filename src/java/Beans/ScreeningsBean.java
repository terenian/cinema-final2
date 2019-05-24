package Beans;

import DAOPackage.CinemaLogger;
import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import GeneralWeb.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
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
    
 
    public Hall getHall() {return hall;}

    public Movie getMovie() {return movie;}

    public Screening getScreening() {return screening;}

    public int getNewOrderID (){
        return newOrderID;
    }
    
    /**
     * gets the number of ordered tickets
     * @return the number of free tickets in a screening
     * @throws SQLException
     */
    public int getNumberOfFreeTickets() throws SQLException {
        int numberOfTakenSeats = ServiceInit.ticketsService().searchTicket(null, null, screeningID, null, null).size();
        numberOfFreeTickets = (hall.getHallLength()*hall.getHallWidth())- numberOfTakenSeats;
        return numberOfFreeTickets;
    }
    
 
    public int getNumberOfDesiredTickets() {return numberOfDesiredTickets;}


    public void setNumberOfDesiredTickets(int num) {
        numberOfDesiredTickets = num;
    }
    
    /**
     * Validator to check if number of desired tickets (in un-marked screening) is valid
     * @param context
     * @param comp
     * @param value
     */
    public void validateFreeTickets (FacesContext context, UIComponent comp,Object value) {
        int numberOfDesiredTicketsString = (Integer) value;
        if (numberOfDesiredTicketsString > numberOfFreeTickets || numberOfDesiredTicketsString < 1) {
            ((UIInput) comp).setValid(false);
            FacesMessage message = new FacesMessage("Maximum Free Tickets is: " + numberOfFreeTickets);
            context.addMessage(comp.getClientId(context), message);
        }
    }
    
    /**
     * return an order total price.
     * @return int with order total price
     */
    public int getOrderPrice() {
        if(seatsForOrder!=null){ //it is a MARKED seats tickets
            orderPrice = seatsForOrder.size()*screening.getPrice();
            return orderPrice;
        } else { //its UNMARKED seats tickets
            orderPrice = numberOfDesiredTickets*screening.getPrice();
            return orderPrice;
        }
    }
    

    /**
     * controls Navigation: check if the screening is reserved-seats or not
     * @return Page Navigation redirect
     */
        public String checkMarkedSeats (){
        if (screening.getMarkedTicket() == 1){
            return ("chooseSeats");
        }
        return ("insertNumberOfTickets");
        
    }
    
 

    /**
     *Control Navigation: check if seats are chosen
     * @return redirects to the same page if not seats were chosen. summary page if there are chosen seats.
     */
        public String checkChosenSeats (){
        CinemaLogger.log(Level.INFO, this.getClass() + " chosen seats are: " + seatsForOrder.toString());
        System.out.println(" = = = seatsForOrder are:" + seatsForOrder);
        if (seatsForOrder == null){
            return ("showSeats");
        }
        return ("showSummary");
        
    }
    
    /**
     * list of available movies 
     * @return list of available movies
     */
    public List<Movie> getMoviesList() { return moviesList;}
    
    /**
     * seatsForOrder is 2 dimension array, each ArrayList is an ArrayList of row&column
     * @return  ArrayList<ArrayList<String>> with seats
     */
    public ArrayList<ArrayList<String>> getSeatsForOrder (){return seatsForOrder;}
    
    /**
     * ChosenSeats as string 
     * @returns chosen seats in a String
     */
    public String getChosenSeats (){return chosenSeats;}
    
    /**
    * generates a List of couples with seats coordinates - row and column.
    * This is used to insert them in the DB and calculating price: price is number of coupls*lists size
     * @param stringOfSeats
    */
    public void setChosenSeats (String stringOfSeats){
        chosenSeats = stringOfSeats;
        if (stringOfSeats.length()>0){
            List<String> seatsList = Arrays.asList(stringOfSeats.split(","));
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
            CinemaLogger.log(Level.INFO, this.getClass() + " seats for order are: " + seatsForOrder);
        }
        else {
            CinemaLogger.log(Level.INFO, this.getClass() + " no seats where selected!");
            seatsForOrder = null;
            chosenSeats = "0";
        }
    }
    
    /**
     * gets a screening list of the selectedMovies
     * @return
     * @throws SQLException
     */
    public List<Screening> getScreeningsListByMovieID() throws SQLException {
        screeningsListByMovieID = ServiceInit.screeningsService().searchScreenings(null,hallID, movieID, null, null,null, null);
        return screeningsListByMovieID;
    }
    /**
     * returns a screening based on the order it's in
     * @param orderID
     * @return Screening
     * @throws java.sql.SQLException
     */
    public Screening getScreeningByOrderID(int orderID) throws SQLException {
        return (ServiceInit.screeningsService().searchScreeningByID(orderID));
    }
    
    /**
     * returns all available screenings by a given HallID
     * @return List of screenings
     * @throws java.sql.SQLException
     */
    public List<Screening> getScreeningsListByHallID() throws SQLException {
        screeningsListByHallID = ServiceInit.screeningsService().searchScreenings(null,hallID, null, null, null,null, null);
        this.createMoviesListfromScreeningList();
        return screeningsListByHallID;
    }
    
    /**
     * Creates a movies list from
     * @throws SQLException
     */
    public void createMoviesListfromScreeningList () throws SQLException {
        int movID;
        moviesList = new ArrayList<>();
        for (int i=0; i<screeningsListByHallID.size(); i++){
            movID=screeningsListByHallID.get(i).getMovieID();
            //System.out.println(" movID is: ========= :" + movID);
           CinemaLogger.log(Level.INFO,this.getClass() + "movID is: ========= :" + movID);
            if (moviesList.isEmpty()) {
                moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                CinemaLogger.log(Level.INFO, this.getClass() + " ==Found Movie Is  ========= :" + moviesList.get(0).toString());
                //System.out.println("==Found Movie Is  ========= :" + moviesList.get(0).toString());
            }
            else{
                for (int j=0; j<moviesList.size(); j++){
                    if (moviesList.get(j).getMovieId() == movID ){
                        continue;
                    }
                    moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                      CinemaLogger.log(Level.INFO,this.getClass() + "movie ADDED is: ========= :" + movID);
                    //System.out.println("movie ADDED is: ========= :" + movID);
                }
            }
        }
    }
    
    /**
     *Saves an order to the DB. first Generates the order and then the tickets.
     */
    public void saveOrder(){
        try {
            newOrderID = ServiceInit.orderService().insertOrder(SessionUtils.getUserId(), orderPrice);
            if(seatsForOrder != null){ //insert MARKED tickets
                for (ArrayList<String> seat : seatsForOrder) {
                    ServiceInit.ticketsService().insertOrder(newOrderID, screeningID, Integer.parseInt(seat.get(0)), Integer.parseInt(seat.get(1)));
                }
            } else { //insert UNMARKED tickets
                for (int i=0; i<numberOfDesiredTickets; i++){
                     ServiceInit.ticketsService().insertOrder(newOrderID, screeningID, -1, -1);
                }
            }
        } catch (SQLException ex) {
             CinemaLogger.log(Level.INFO,this.getClass() + " ORDER INSERTION PROCCESS FAILED" + moviesList.get(0).toString());
            //System.out.println("ORDER INSERTION PROCCESS FAILED");
        }
    }
    
    
    private int movieID;

    /**
     * selected Movie's ID
     * @return
     */
    public int getMovieID() {return movieID;}

    /**
     * sets a movieID and all movie details from the DB
     * @param movie
     * @throws SQLException
     */
    public void setMovieID(int movie) throws SQLException {
        this.movieID = movie;
        this.movie= ServiceInit.moviesService().searchMoviesbyID(movieID).get(0);
        CinemaLogger.log(Level.INFO, this.getClass() + " selected Movie is: " + this.movie.toString() );
    }
    
    private int screeningID;

    /**
     *
     * @return selected ScreeningID
     */
    public int getScreeningID() {return screeningID;}

    /**
     * sets a selected Screening
     * @param scr
     * @throws SQLException
     */
    public void setScreeningID(int scr) throws SQLException {
        this.screeningID = scr;
        this.screening = ServiceInit.screeningsService().searchScreenings(screeningID, null, null, null, null, null, null).get(0);
        CinemaLogger.log(Level.INFO,this.getClass() + " selected Screen is: " + screening.toString());
    }
    
    private int hallID;

    /**
     *
     * @return selected hall ID
     */
    public int getHallID() {return hallID;}

    /**
     *
     * @param hallToSet
     * @throws SQLException
     */
    public void setHallID (int hallToSet) throws SQLException{
        cleanOrder();
        CinemaLogger.log(Level.INFO,this.getClass() + " selected hall is: " + hallToSet);
        //System.out.println("hall is: ========= :" + hallToSet);
        this.hallID = hallToSet;
        this.hall= ServiceInit.hallsService().searchHalls(hallID, "").get(0);
        this.getScreeningsListByHallID();
    }
    
    /**
     * Cleans local bean parameters
     */
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



