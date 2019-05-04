/*
* This class represents a Hall item in the Hall Table.
*/
package EntitiesLayer;

import java.io.Serializable;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Hall implements Serializable{
    
    private Integer hallID;
    private Integer hallWidth;
    private Integer hallLength;
    private String hallName;

    
    public Hall (Integer hID, String name, Integer hWidth, Integer hLength) {
        
        this.hallID = hID;
        this.hallWidth = hWidth;
        this.hallLength = hLength;
        this.hallName = name;

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


