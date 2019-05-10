/*
* This class represents a User item in the User Table.
*/
package EntitiesLayer;
/**
 *
 * @author Eran Z. & Itzik W.
 */
public class User {
    
    private int userID;
    private int userRoleID;
    private String password;
    private String userName;
    private String address;

    
    public User(){
        
    }
    public User(int uID, int roleID, String name, String pass, String address){
        
        this.userID = uID;
        this.userRoleID = roleID;
        this.password = pass;
        this.userName = name;
        this.address = address;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public int getUserRoleID() {
        return userRoleID;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String user) {
        this.userName=user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAddress() {
        return address;
    }
    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * @param userRoleID the userRoleID to set
     */
    public void setUserRoleID(int userRoleID) {
        this.userRoleID = userRoleID;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
   
    public String toString(){
        return  "[" + getUserID()+" , "+
                 getUserRoleID() + " , "+
                getUserName() + " , " +
                getPassword() + " , " +
                getAddress() + "]";
    }

    
   
    
}

