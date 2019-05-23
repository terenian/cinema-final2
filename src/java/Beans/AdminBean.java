package Beans; 

import DAOPackage.CinemaLogger;
import GeneralWeb.SessionUtils;
import java.io.Serializable;
import java.util.logging.Level;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;


/**
 *
 * @author Eran.z & Itzik W.
 */
@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;	

    /**
     * validate login
     * @return Login Navigation page if success, null if false
     */
	public String isLoggedIn(){
            if (!SessionUtils.getUserRole().equals("admin"))
                return "Login";
            else{
                CinemaLogger.log(Level.INFO, this.getClass() + " ");
                return null;
            }
	}


    
}
