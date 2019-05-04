/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

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
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;
	
	//private String pwd;
	private User user;
        private String msg;
	//private String user;

        public LoginBean() {
            this.user = new User();
        }
        public User getUser() {
		return user;
	}
	public String getMsg() {
		return msg;
	}

	

	//validate login
	public String validateUsernamePassword() throws SQLException {
		int checkUser = ServiceInit.UsersService().validateUser(user.getUserName(), user.getPassword());
		if (checkUser > 1) 
                    {
			HttpSession session = SessionUtils.getSession();
                        session.setAttribute("username", user.getUserName());
			if (checkUser == 3)
                        {
                            session.setAttribute("role", "Admin");
                            return "Admin";
                        }
                        else
                        {
                            session.setAttribute("role", "User");
                            return "MyOrders";  
                        }
                    }
                else 
                    {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username - test and Passowrd",
							"Please enter correct username and Password"));
			return "Welcome";
                    }
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}

    
}
