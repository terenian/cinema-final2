/*
* This class represents an Order item in the Orders Table.
*/
package EntitiesLayer;

import java.io.Serializable;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Ticket implements Serializable{
    
    private static final long serialVersionUID = 4564564564L;
    
    private Integer orderID;
    private Integer screeningID;
    private Integer rowNum;
    private Integer columnNum;
    private Integer used;

    
    public Ticket(){}
    /*
    public Ticket( Ticket t){
        this.orderID=t.orderID;
        this.screeningID=t.screeningID;
        this.rowNum=t.rowNum;
        this.columnNum=t.columnNum;
        this.used=t.used;
    }*/
    
    public Ticket(Integer id, Integer sID, Integer row, Integer column){
        this.orderID = id;
        this.screeningID = sID;
        this.rowNum = row;
        this.columnNum = column;
        this.used=0;
    }
    
    //=============WHAT IS THE ID? UNMARK TICKES DOES NEED A UNIQUE ID!
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
        return  "[" + orderID+" , "+
                screeningID+" , "+
                rowNum + " , "+
                columnNum + " , "+
                used + "]";
    }
   
    
}


