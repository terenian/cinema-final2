package Beans;

import javax.inject.Named;
import java.io.Serializable;
import EntitiesLayer.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;


/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "ScreeningsBean")
@ManagedBean
@ApplicationScoped
public class ScreeningsBean implements Serializable {
    
    private Hall hall;
    public Hall getHall() {return hall;}
    
    private Movie movie;
    public Movie getMovie() {return movie;}
    
    private Screening screening;
    public Screening getScreening() {return screening;}
    
    private List<Screening> screeningsListByHallID;
    private List<Screening> screeningsListByMovieID; 
    
    private List<Movie> moviesList;
    public List<Movie> getMoviesList() { return moviesList;}
    
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
    
    
    /**
     * Creates a new instance of ScreeningsBean
     */
    
    public ScreeningsBean() {
        
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
}



