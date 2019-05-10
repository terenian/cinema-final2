package Beans;

import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;


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


    public Hall getHall() {return hall;}
    public Movie getMovie() {return movie;} 
    public Screening getScreening() {return screening;}
    public int getNewOrderID (){return newOrderID;}
    private List<Screening> screeningsListByHallID;
    private List<Screening> screeningsListByMovieID;
    private List<Movie> moviesList;
     
    public int getOrderPrice() {
        if(seatsForOrder!=null){
            orderPrice = seatsForOrder.size()*screening.getPrice();
            return orderPrice;
        }
        return 0;
    }
    
    public String checkNavigation (){
        System.out.println(" = = = seatsForOrder are:" + seatsForOrder);
        if (seatsForOrder == null){
            return ("showSeats");
        }
        return ("showSummary");
        
    }
    
    public List<Movie> getMoviesList() { return moviesList;}
    
    private ArrayList<ArrayList<String>> seatsForOrder ;
    public ArrayList<ArrayList<String>> getSeatsForOrder (){return seatsForOrder;}
    
    private String chosenSeats;
    //public String[] getChosenSeats (){return chosenSeats;}
    public String getChosenSeats (){return "";}
    public void setChosenSeats (String stringOfSeats){
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
            System.out.println ("seatsForOrde is: " + seatsForOrder)   ;
        }
        else {
            seatsForOrder = null;
            chosenSeats = "0";
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
    public void setHallID (int hallToSet) throws SQLException, Throwable{
        System.out.println("hall is: ========= :" + hallToSet);
        this.hallID = hallToSet;
        this.hall= ServiceInit.hallsService().searchHalls(hallID, "").get(0);
        this.getScreeningsListByHallID();
        //this.createMoviesListfromScreeningList();
    }
    
    public List<Screening> getScreeningsListByMovieID() throws ClassNotFoundException, SQLException, Throwable {
        screeningsListByMovieID = ServiceInit.screeningsService().searchScreenings(null,hallID, movieID, null, null,null, null);
        return screeningsListByMovieID;
    }
    
    public List<Screening> getScreeningsListByHallID() throws ClassNotFoundException, SQLException, Throwable {
        screeningsListByHallID = ServiceInit.screeningsService().searchScreenings(null,hallID, null, null, null,null, null);
        this.createMoviesListfromScreeningList();
        return screeningsListByHallID;
    }
    
    public void createMoviesListfromScreeningList () throws SQLException, Throwable {
        int movID;
        moviesList = new ArrayList<>();
        for (int i=0; i<screeningsListByHallID.size(); i++){
            movID=screeningsListByHallID.get(i).getMovieID();
            System.out.println("movID is: ========= :" + movID);
            if (moviesList.isEmpty()) {
                moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                System.out.println("==Found Movie Is  ========= :" + moviesList.get(0).toString());
            }
            else{
                for (int j=0; j<moviesList.size(); j++){
                    if (moviesList.get(j).getMovieId() == movID ){
                        continue;
                    }
                    moviesList.add(ServiceInit.moviesService().gethMoviesbyID(movID));
                    System.out.println("movie ADDED is: ========= :" + movID);
                }
            }
        }
    }
    
    public void saveOrder(){
        try {
            //TODO: replace 1 with real user ID from Session
            //TODO: HOW TO MAKE IT ALL IN ONE PICE?
            newOrderID = ServiceInit.orderService().insertOrder(1, orderPrice);
            for (ArrayList<String> seat : seatsForOrder) {
                ServiceInit.ticketsService().insertOrder(newOrderID, screeningID, Integer.parseInt(seat.get(0)), Integer.parseInt(seat.get(1)));
            }

            this.moviesList = null;
            this.screeningsListByHallID = null;
            this.screeningsListByMovieID = null;
            this.hallID = 0;
            this.movieID = 0;
            this.screeningID = 0;
            this.chosenSeats = "";

        } catch (SQLException ex) {
            //Logger.getLogger(ScreeningsBean.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ORDER INSERTION PROCCESS FAILED");
        }
    }
}



