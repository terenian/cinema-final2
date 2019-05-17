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
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author eran.z
 */
@Named(value = "userBean")
@SessionScoped
public class UserBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;
	
        private List<User> userList;
	private User user;  
        private String msg;
        

        public UserBean() {
            this.user = new User();
        }
        public User getUser() {
		return user;
	}
	public String getMsg() {
		return msg;
	}
        
        public List<User> getUserList() throws  SQLException 
        {
            userList = ServiceInit.UsersService().getAllUsers();
            return userList;
        }
        public String changeRole(int userID,String roleName) throws SQLException
        {
            if (roleName.equals(Role.ROLE_USER))
               ServiceInit.UsersService().updateRole(userID,Role.ROLE_ADMIN);
            else
                ServiceInit.UsersService().updateRole(userID,Role.ROLE_USER);
            return "/Admin/UserAdmin";
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
