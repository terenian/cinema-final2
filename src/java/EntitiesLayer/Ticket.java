
package EntitiesLayer;

import java.io.Serializable;

/**
 *This class represents a Ticket item in the Tickets Table.
 * @author Eran Z. & Itzik W.
 */
public class Ticket implements Serializable{
    
    private static final long serialVersionUID = 4564564564L;
    
    private Integer ticketID;
    private Integer orderID;
    private Integer screeningID;
    private Integer rowNum;
    private Integer columnNum;
    private Integer used;

    
    public Ticket(){}
    
    public Ticket(Integer tID, Integer ordID, Integer sID, Integer row, Integer column, Integer used){
        this.ticketID = tID;
        this.orderID = ordID;
        this.screeningID = sID;
        this.rowNum = row;
        this.columnNum = column;
        this.used=used;
    }
    
    
    public Integer getTicketID(){
        return ticketID;
    }
    
    public Integer getOrderID() {
        return orderID;
    }
    
    public Integer getScreeningID() {
        return screeningID;
    }
    
    public Integer getRowNum() {
        return rowNum;
    }
    
    public Integer getColumnNum() {
        return columnNum;
    }
    
    public Integer getUsed(){
        return used;
    }
    
   
    public String toString(){
        return  "[" + ticketID +" , "+
                orderID+" , "+
                screeningID+" , "+
                rowNum + " , "+
                columnNum + " , "+
                used + "]";
    }
    public String ticketLocation()
    {
        if (this.rowNum == -1 && this.columnNum == -1)
            return "X";
        else
            return "Row:" + this.rowNum + " Col:" + this.columnNum;
    }
    
    /**
     * return a Ticket object as sequential int array. Used to pass easily  via WebService 
     * @return
     */
    public int[] asIntArr(){
         int[] intArr = new int[6];
         intArr[0]=orderID;
         intArr[1]=screeningID;
         intArr[2]=rowNum;
         intArr[3]=columnNum;
         intArr[4]=used;
         intArr[5]=ticketID;
         return intArr;
     }
   
    
}


