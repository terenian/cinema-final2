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
 * @author Eran.z & Itzik W.
 */
@Named(value = "adminBean")
@SessionScoped
public class AdminBean implements Serializable {

    	private static final long serialVersionUID = 1094801825228386363L;	
	//validate login

    /**
     *
     * @return
     */
	public String isLoggedIn(){
            if (!SessionUtils.getUserRole().equals("admin"))
                return "Login";
            else
                return null;
	}


    
}
