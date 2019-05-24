package Beans;

import DAOPackage.CinemaLogger;
import GeneralWeb.SessionUtils;
import EntitiesLayer.User;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *Provides Bean services to Registration -related actions
 * @author Eran.z & Itzik W.
 */
@Named(value = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;
	
	private User user;  

    /**
     * Default constructor
     */
    public RegistrationBean() {
            this.user = new User();
        }

    /**
     * 
     * @return User
     */
    public User getUser() {
		return user;
	}
        
    /**
     * Adds a new user to the DB
     * @return Navigation Page redircet
     * @throws SQLException
     */
    public String addUser() throws SQLException {
		if(ServiceInit.UsersService().userNameExists(user.getUserName()))
                {
                  FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN,
							"username already exists",
							"Please choose another"));
                    CinemaLogger.log(Level.SEVERE, this.getClass() + user.getUserName() + " already exists!");
                    return "Registration";  
                }
                else{
                    ServiceInit.UsersService().addNewUser(user);
                    SessionUtils.logout();
                    return "Login";
                }
        }
        
}
