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
    private String userRoleName;
    private String firstName;
    private String lastName;
    private String password;
    private String userName;
    private String email;
    private String phoneNumber;

    
    public User(){
        this.userRoleName = Role.ROLE_USER;
    }

    public User(int userID, String firstName, String lastName, String password, String userName, String email, String phoneNumber) {
        this.userID = userID;
        this.userRoleName = Role.ROLE_USER;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    
    public int getUserID() {
        return userID;
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
  
    /**
     * @param userID the userID to set
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }
   

    /**
     * @return the userRoleName
     */
    public String getUserRoleName() {
        return userRoleName;
    }

    /**
     * @param userRoleName the userRoleName to set
     */
    public void setUserRoleName(String userRoleName) {
        this.userRoleName = userRoleName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @param phoneNumber the phoneNumber to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

        public String toString(){
        return  "[" + getUserID()+" , "+
                getUserName()+ " , "+
                getUserRoleName() + " , " +
                getFirstName() + " , " +
                getLastName() + " , " +
                getEmail() + " , " +
                getPassword() + " , " +
                getPhoneNumber()+ "]";
    }

   
    
}

