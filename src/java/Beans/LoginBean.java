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
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;
	
	private User user;  
        private String msg;

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

	//logout event, invalidate session
	public String logout() {
		SessionUtils.logout();
		return "logout";
	}

    
}
