package Beans;

import DAOPackage.CinemaLogger;
import EntitiesLayer.Role;
import GeneralWeb.SessionUtils;
import EntitiesLayer.User;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *Provides Bean services to User-related actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1094801825228386363L;
    
    private List<User> userList;
    private User user;
    //private String msg;
    
    /**
     *Default constructor and generates new User without data for further use
     */
    public UserBean() {
        this.user = new User();
    }

    public User getUser() {
        return user;
    }


    /**
     *
     * @return List of users
     * @throws SQLException
     */
    public List<User> getUserList() throws  SQLException
    {
        userList = ServiceInit.UsersService().getAllUsers();
        return userList;
    }

    /**
     * Sets the user roles in the DB
     * @param userID
     * @param roleName
     * @return Navigation page admin
     * @throws SQLException
     */
    public String changeRole(int userID,String roleName) throws SQLException
    {
        if (roleName.equals(Role.ROLE_USER)){
            ServiceInit.UsersService().updateRole(userID,Role.ROLE_ADMIN);
            CinemaLogger.log(Level.INFO, this.getClass() + "UserRole is " + Role.ROLE_ADMIN);
        }
        else{
            ServiceInit.UsersService().updateRole(userID,Role.ROLE_USER);
            CinemaLogger.log(Level.INFO, this.getClass() + "UserRole is " + Role.ROLE_USER);
        }
        return "/Admin/UserAdmin";
    }

    /**
     * Validates Login
     * @return Navigation Page redirect
     * @throws SQLException
     */
        public String validateUsernamePassword() throws SQLException {
        User checkedUser = ServiceInit.UsersService().validateUser(user.getUserName(), user.getPassword());
        if(checkedUser != null)
        {
            HttpSession session = SessionUtils.getSession();
            //this.user.setUserRoleID(checkUser);
            session.setAttribute("username",checkedUser.getUserName() );
            session.setAttribute("userid",checkedUser.getUserID());
            session.setAttribute("role",checkedUser.getUserRoleName());
            return "Connected";
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "Login";
        }
    }
    

    /**
     * Logout session
     * @return page navigation logout redirect
     */
        public String logout() {
        SessionUtils.logout();
        return "logout";
    }
    
    
}
