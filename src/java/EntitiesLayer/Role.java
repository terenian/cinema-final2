/*
* This class represents an Role item in the Roles Table.
*/
package EntitiesLayer;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Role {
    
    
    public static final String ROLE_ADMIN="Admin";
    public static final String ROLE_USER="User";
    private String roleName;

    
    public Role(String name){
        this.roleName = name;
    }
    
    public String getRoleName() {
        return roleName;
    }
  
    public String toString(){
        return roleName ;
    }
   
    
}

