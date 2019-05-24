package EntitiesLayer;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * This class represents a Screening item in the Screening Table.
 * @author Eran Z. & Itzik W.
 */
public class Screening {
    
    
    private Integer screeningID;
    private Integer hallID;
    private Integer movieID;
    private Integer price;
    private Integer markedTicket;
    private Date date;
    private Date time;

    public void setScreeningID(Integer screeningID) {
        this.screeningID = screeningID;
    }

    public void setHallID(Integer hallID) {
        this.hallID = hallID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setMarkedTicket(Integer markedTicket) {
        this.markedTicket = markedTicket;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Date time) {
        this.time = time;
    }


    
    public Screening()
    {
        
    }
    public Screening(Integer sID, Integer hID, Integer mID, Integer price, Integer mt, Date date, Date time){
        
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
    public Date getDate() {
        return date;
    }
    public Date getTime() {
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
    public String getScreeningTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String screeningTime = sdf.format(date);
        sdf.applyPattern("HH:mm:ss");
        return screeningTime +" " + sdf.format(time);
    }
    public boolean isPast()
    {
        Date today = new Date();
        return (this.date.before(today));
    }
   
    
}

