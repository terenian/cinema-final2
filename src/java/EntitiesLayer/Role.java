/*
* This class represents an Role item in the Roles Table.
*/
package EntitiesLayer;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Role {
    
    
    private Integer roleID;
    private String roleName;

    
    public Role(Integer id, String name){
        this.roleID = id;
        this.roleName = name;
    }
    
    public Integer getRoleID() {
        return roleID;
    }
    
    public String getRoleName() {
        return roleName;
    }
  
    public String toString(){
        return  "[" + roleID+" , "+
                roleName + "]";
    }
   
    
}

