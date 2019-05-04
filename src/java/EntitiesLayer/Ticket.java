/*
* This class represents an Order item in the Orders Table.
*/
package EntitiesLayer;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Ticket {
    
    
    private Integer orderID;
    private Integer screeningID;
    private Integer rowNum;
    private Integer columnNum;

    
    public Ticket(Integer id, Integer sID, Integer row, Integer column){
        this.orderID = id;
        this.screeningID = sID;
        this.rowNum = row;
        this.columnNum = column;
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
    
   
    public String toString(){
        return  "[" + orderID+" , "+
                screeningID+" , "+
                rowNum + " , "+
                columnNum + "]";
    }
   
    
}


