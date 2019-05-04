/*
* This class represents a Screening item in the Screening Table.
*/
package EntitiesLayer;
/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Screening {
    
    
    private Integer screeningID;
    private Integer hallID;
    private Integer movieID;
    private Integer price;
    private Integer markedTicket;
    private String date;
    private String time;

    
    public Screening(Integer sID, Integer hID, Integer mID, Integer price, Integer mt, String date, String time){
        
        this.screeningID = sID;
        this.hallID = hID;
        this.movieID = mID;
        this.price = price;
        this.markedTicket = mt;
        this.date = date;
        this.time = time;
    }
    
    public Integer getScreeningID() {
        return screeningID;
    }
    
    public Integer getHallID() {
        return hallID;
    }
    
    public Integer getMovieID() {
        return movieID;
    }
    
    public Integer getPrice() {
        return price;
    }
    public Integer getMarkedTicket() {
        return markedTicket;
    }
    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    
   
    public String toString(){
        return  "[" + screeningID+" , "+
                hallID +" , "+
                movieID + " , "+
                 price + " , "+
                 markedTicket + " , "+
                 date + " , "+
                time + "]";
    }
   
    
}

