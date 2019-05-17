/*
* This class gives a service of CRUD actions on Users Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Movie;
import EntitiesLayer.Role;
import EntitiesLayer.User;
import java.util.List;



/**
 *
 * @author Eran Z. & Itzik W
 */
public class UsersService {
    
    
    private DBConnector dbConnection;
    
    private final String MOVIE_INSERT = "insert into cinema.movies (MovieName, MovieLength) values (?,?)";
    //Changed by Eran 9.4.19 private final String MOVIE_SEARCH = "select * from cinema.movies where MovieID like (?) and MovieName like (?) and MovieLength like (?)";
    
    
    public UsersService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    public boolean userNameExists(String username) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement valUser = null;
        valUser = c.prepareStatement("select UserName from cinema.users where UserName = (?)");
        valUser.setString(1, username);
        
        ResultSet rs = valUser.executeQuery();
        if(!rs.first())
        {
            return false;
        }
        else 
        {
            return true;
        }
    }
    
    public boolean addNewUser(User newUser) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement valUser = null;
        valUser = c.prepareStatement("INSERT INTO `cinema`.`users` (`RoleName`, `UserName`, `Password`, `FirstName`, `LastName`, `PhoneNumber`, `Email`)" +
                                     "VALUES ((?),(?),(?),(?),(?),(?),(?))");
        valUser.setString(1, newUser.getUserRoleName());
        valUser.setString(2, newUser.getUserName());
        valUser.setString(3, newUser.getPassword());
        valUser.setString(4, newUser.getFirstName());
        valUser.setString(5, newUser.getLastName());
        valUser.setString(6, newUser.getPhoneNumber());
        valUser.setString(7, newUser.getEmail());
        
        
        return(valUser.executeUpdate() > 0 );
 
    }
    public boolean updateRole(int userID,String newRole) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("update users set RoleName = (?) where UserID = (?)");
        prepStat.setString(1, newRole);
        prepStat.setInt(2, userID);
        return (prepStat.executeUpdate()>0);
        
    }
    public List<User> getAllUsers() throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select * from users");
        
        ResultSet rs = prepStat.executeQuery();
        List<User> list = new ArrayList<User>();
        while(rs.next()){
            User tempUser = new User(rs.getInt(1),rs.getString(2),rs.getString(5),rs.getString(6),"",rs.getString(3),rs.getString(8),rs.getString(7));
            list.add(tempUser);
        }
        return list;
    }
    public User validateUser (String username,String password)
            throws SQLException{
        
        Connection c = dbConnection.getConnection();
        PreparedStatement valUser = null;
        valUser = c.prepareStatement("select Password from cinema.users where UserName = (?)");
        valUser.setString(1, username);
        
        ResultSet rs = valUser.executeQuery();
        if(!rs.first() || !rs.getString(1).equals(password))
        {
            return null;
        }
        else
        {
            valUser = c.prepareStatement("select UserID,UserName,RoleName from cinema.users where username = (?)");
            valUser.setString(1,username);
            rs = valUser.executeQuery();
            rs.next();
            User validUser =  new User(rs.getInt(1),"","","",rs.getString(2),"","");
            validUser.setUserRoleName(rs.getString(3));
            return validUser;
        }   
    }
    
    
}
