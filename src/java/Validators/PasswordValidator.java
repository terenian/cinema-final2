/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author eran.z
 */
@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator  {
    
        @Override
	public void validate(FacesContext context, UIComponent component,
		Object value) throws ValidatorException {

	  String password = value.toString();

	  UIInput uiInputConfirmPassword = (UIInput) component.getAttributes()
		.get("pswconfirmation");
	  String confirmPassword = uiInputConfirmPassword.getSubmittedValue()
		.toString();

	  // Let required="true" do its job.
	  if (password == null || password.isEmpty() || confirmPassword == null
		|| confirmPassword.isEmpty()) {
			return;
	  }
          
          //password complexity
          if(!(
                (password.matches("(?=.*[A-Z]).*")) &&
                (password.matches("(?=.*[0-9]).*")) && 
                (password.matches("(?=.*[a-z]).*"))
              ))
              
          {
              uiInputConfirmPassword.setValid(false);
		throw new ValidatorException(new FacesMessage(
			"Password must contain at least 1 uppercase letter, 1 lowercase letter and 1 digit."));
          }
          else if (!password.equals(confirmPassword)) 
          {
		uiInputConfirmPassword.setValid(false);
		throw new ValidatorException(new FacesMessage(
			"Password must match confirm password."));
	  }

	}
    
}
