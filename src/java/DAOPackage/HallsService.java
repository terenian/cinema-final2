/*
* This class gives a service of CRUD actions on Halls Table.
*/
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Hall;


/**
 *
 * @author Eran Z. & Itzik W
 */
public class HallsService {
    
    
    private DBConnector dbConnection;
    
    private final String HALL_INSERT = "insert into cinema.halls (HallID, HallName,HallWidth,HallLength) values (?,?,?,?)";
    private final String HALL_SEARCH = "select * from cinema.halls where HallID like (?) and HallName like (?)";
    private final String HALL_UPDATE = "update cinema.halls set HallName=(?), set HallWidth=(?), HallLength=(?) where HallID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    
    public HallsService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    
    /*
    * Inserts a new hall
    */
    public Integer insertHall(String name, Integer width,  Integer length)
            throws SQLException{
        
        //logger.info("insert Hall("+hallID+","+userID+","+price+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement hallInsertSTM = null;
        hallInsertSTM = c.prepareStatement(HALL_INSERT);
        
        hallInsertSTM.setString(1, name);
        hallInsertSTM.setInt(2, width);
        hallInsertSTM.setInt(3, length);
        int result = 0;
        hallInsertSTM.executeUpdate();
        if (result >= 0) {
            System.out.println("SuccessInsert");
        }
        else{
            System.out.println("UnSeccessed insert!");
        }
        return result;
    }
    
    /*
    * Search for hall
    */
    public ArrayList<Hall> searchHalls(Integer id, String name)
            throws SQLException{
        //logger.info("search Hall("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement hallSearchSTM = null;
        hallSearchSTM = c.prepareStatement(HALL_SEARCH);
        if (id==null) {
            hallSearchSTM.setString(1, "%");
        }
        else{
            hallSearchSTM.setInt(1, id);
        }
        if (name.equals("")) {
            hallSearchSTM.setString(2, "%");
        }
        else{
            hallSearchSTM.setString(2, name);
        }
        
        System.out.println("hallSearchSTM IS: " + hallSearchSTM.toString());
        
        ResultSet rs = hallSearchSTM.executeQuery();
        ArrayList<Hall> list = new ArrayList<Hall> ();
        while(rs.next()){
            Hall hll = new Hall(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4));
            list.add(hll);
        }
        return list;
    }
    
    /*
    * update hall
    */
    public boolean updateHall(Integer id, String name ,Integer length)
            throws SQLException{
        //logger.info("update Hall("+id+","+name+","+description+","+parentId+")");
        if (id == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement hallUpdateSTM = null;
        hallUpdateSTM = c.prepareStatement(HALL_UPDATE);
                
        System.out.println("hallUpdateSTM IS: " + hallUpdateSTM.toString());
        if (length == null) {
            hallUpdateSTM.setNull(1, java.sql.Types.INTEGER);
        }
        else
        {
            hallUpdateSTM.setInt(1,length);
        }
    
        hallUpdateSTM.setString(2, name);
        
        System.out.println("hallUpdateSTM IS: " + hallUpdateSTM.toString());
        
        Integer i = hallUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    
}
