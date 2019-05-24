package EntitiesLayer;

import java.io.Serializable;

/**
 * This class represents a Hall item in the Hall Table.
 * @author Eran Z. & Itzik W.
 */
public class Hall implements Serializable{
    
    private Integer hallID;
    private Integer hallWidth;
    private Integer hallLength;
    private String hallName;

    
    public Hall () {

    }
    public Hall (Integer hID, String name, Integer hWidth, Integer hLength) {
        
        this.hallID = hID;
        this.hallWidth = hWidth;
        this.hallLength = hLength;
        this.hallName = name;

    }

    public void setHallWidth(Integer hallWidth) {
        this.hallWidth = hallWidth;
    }

    public void setHallLength(Integer hallLength) {
        this.hallLength = hallLength;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }
    
    public Integer getHallID() {
        return hallID;
    }
    
    public Integer getHallWidth() {
        return hallWidth;
    }
    
    public Integer getHallLength() {
        return hallLength;
    }
  
    public String getHallName() {
        return hallName;
    }
   
    public String toString(){
        return  "[" + hallID +" , "+
                hallWidth + " , "+
                 hallLength + " , "+
                 hallName + "]";
    }
   
    
}


