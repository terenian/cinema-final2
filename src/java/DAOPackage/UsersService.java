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
    
    public int validateUser (String username,String password)
            throws SQLException{
        
        Connection c = dbConnection.getConnection();
        PreparedStatement valUser = null;
        valUser = c.prepareStatement("select Password from cinema.users where UserName = (?)");
        valUser.setString(1, username);
        
        ResultSet rs = valUser.executeQuery();
        if(!rs.first())
        {
            return 0;//wrong username
        }
        else if (!rs.getString(1).equals(password))
        {
            return 1;//wrong password
        }
        else
        {
            valUser = c.prepareStatement("select RoleName from cinema.roles,cinema.users where username = (?) and "
                    + "cinema.roles.RoleID = cinema.users.RoleID ");
            valUser.setString(1,username);
            rs = valUser.executeQuery();
            rs.next();
            if(rs.getString(1).equals("Admin"))
                return 3;//admin user
            else
                return 2;//normal user
        }   
    }
    
    
}
