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
@Named(value = "NewOrderBean")
@ManagedBean
@ApplicationScoped
public class NewOrderBeanTest implements Serializable {
    
    private List<Screening> screeningsListByHallID;
    private List<Screening> screeningsListByMovieID;
    
    private List<Movie> moviesList;
    public List<Movie> getMoviesList() { return moviesList;}
    
    private int movieID;
    public int getMovieID() {return movieID;}
    public void setMovieID(int movie) {this.movieID = movie;}
    
    private Hall hall;
    public Hall getHall() {return this.hall;}
    public void setHallID (int hallID){
        System.out.println("hall is: ========= :" + hall);
        this.hall = hall;
    }
    
    
    /**
     * Creates a new instance of ScreeningsBean
     */
    
    public NewOrderBeanTest() {
        
    }
    
    
    public List<Screening> getScreeningsListByMovieID() throws ClassNotFoundException, SQLException, Throwable {
        screeningsListByMovieID = ServiceInit.screeningsService().searchScreenings(null,null, movieID, null, null,null, null);
        return screeningsListByMovieID;
    }
    
    public List<Screening> getScreeningsListByHallID() throws ClassNotFoundException, SQLException, Throwable {
        screeningsListByHallID = ServiceInit.screeningsService().searchScreenings(null,hall.getHallID(), null, null, null,null, null);
        this.createMoviesListfromScreeningList();
        return screeningsListByHallID;
    }
    
    public void createMoviesListfromScreeningList () throws SQLException {
        int movID;
        for (int i=0; i<screeningsListByHallID.size(); i++){
            movID=screeningsListByHallID.get(i).getMovieID();
            System.out.println("movID is: ========= :" + movID);
            moviesList = new ArrayList<>();
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



