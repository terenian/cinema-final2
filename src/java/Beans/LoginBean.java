/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Beans;

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
	
	private String pwd;
	private String msg;
	private String user;

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	//validate login
	public String validateUsernamePassword() throws SQLException {
		int checkUser = ServiceInit.UsersService().validateUser(user, pwd);
		if (checkUser > 1) 
                    {
			HttpSession session = SessionUtils.getSession();
                        session.setAttribute("username", user);
			if (checkUser == 3)
                        {
                            session.setAttribute("role", "Admin");
                            return "admin";
                        }
                        else
                        {
                            session.setAttribute("role", "User");
                            return "user";  
                        }
                    }
                else 
                    {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username-test and Passowrd",
							"Please enter correct username and Password"));
			return "login";
                    }
	}

	//logout event, invalidate session
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		return "login";
	}

    
}
