/*
* This class represents an Order item in the Orders Table.
*/
package EntitiesLayer;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Order {
    
    
    private Integer orderID;
    private Integer userID;
    private Integer price;

    
    public Order(Integer id, Integer user, Integer price){
        this.orderID = id;
        this.userID = user;
        this.price = price;
    }
    
    public Integer getOrderID() {
        return orderID;
    }
    
    public Integer getUserID() {
        return userID;
    }
    
    public Integer getPrice() {
        return price;
    }
    
  
    public String toString(){
        return  "[" + orderID+" , "+
                userID+" , "+
                price + "]";
    }
   
    
}

