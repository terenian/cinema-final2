/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

import EntitiesLayer.Role;
import GeneralWeb.SessionUtils;
import EntitiesLayer.User;
import java.io.Serializable;
import java.sql.SQLException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eran.z
 */
@Named(value = "registrationBean")
@SessionScoped
public class RegistrationBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;
	
	private User user;  
        private String msg;

        public RegistrationBean() {
            this.user = new User();
        }
        public User getUser() {
		return user;
	}
	public String getMsg() {
		return msg;
	}
        
        public String addUser() throws SQLException {
		if(ServiceInit.UsersService().userNameExists(user.getUserName()))
                {
                  FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"username already exists",
							"Please choose another"));
                    return "Registration";  
                }
                else{
                    return "Login";
                }
        }
        
}
