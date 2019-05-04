/*
* This class represents a User item in the User Table.
*/
package EntitiesLayer;
/**
 *
 * @author Eran Z. & Itzik W.
 */
public class User {
    
    private Integer userID;
    private Integer userRoleID;
    private String password;
    private String userName;
    private String address;

    
    public User(Integer uID, Integer roleID, String name, String pass, String address){
        
        this.userID = uID;
        this.userRoleID = roleID;
        this.password = pass;
        this.userName = name;
        this.address = address;
    }
    
    public Integer getUserID() {
        return userID;
    }
    
    public Integer getUserRoleID() {
        return userRoleID;
    }
    
    public String getUserName() {
        return userName;
    }
    public String getPassword() {
        return password;
    }
    
    public String getAddress() {
        return address;
    }
    
   
    public String toString(){
        return  "[" + userID+" , "+
                 userRoleID + " , "+
                userName + " , " +
                password + " , " +
                address + "]";
    }
   
    
}

