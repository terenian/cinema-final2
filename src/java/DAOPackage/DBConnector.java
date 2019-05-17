/*
* This class establishe a connection with the DB using MySQL Connector.
*/
package DAOPackage;
import Beans.ServiceInit;
import DAOPackage.Configuration;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Eran Z. & Itzik W
 */
public class DBConnector {
    private String user;
    private String pass;
    private String host;
    private String dbName;
    private String connectionString;
    private Connection myConn = null;
    private Statement myStmt = null;
    private ResultSet myRs = null;
    private ResultSetMetaData myRsMD;
    private boolean connectionIsOn = false;
    
     private Logger logger;
    /*
    gets db name, username and passwords and creates a connection.
    */
    public DBConnector(Configuration c) throws SQLException {
        
        user = c.getUserName();
        pass = c.getPassword();
        host = c.getDbServer();
        dbName = c.getDbName();
        logger = ServiceInit.getLogger();
        
        
        connectionString = "jdbc:mysql://"+ 
                host +"/" + 
                dbName + "?useSSL=false&useUnicode=true&serverTimezone=Asia/Jerusalem";
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            //1. get a connection to db
            myConn = DriverManager.getConnection(connectionString, user, pass);
            connectionIsOn = true;
            
            System.out.println(this.getClass().toString() + ": CONNECTION SUCCEEDED");
            //logger.log(Level.INFO, "{0}: CONNECTION SUCCESS", this.getClass().toString());
            //===========TEST===========s
            //testConnection();
        }
        catch (Exception e){
            System.out.println(this.getClass().toString() + ": CONNECTION FAILED");
            //logger.log(Level.INFO, "{0}: CONNECTION FAILED", this.getClass().toString());
            e.printStackTrace();
        }
        
        
    }
    
    public Connection getConnection (){
        if (connectionIsOn){
            return myConn;
        }
        return null;
    }

    /*
    * closes the connection
    */
    public void closeConnection() throws SQLException{
        if (myRs != null) {
            myRs.close();
        }
        if (myStmt != null){
            myStmt.close();
        }
        if (myConn != null){
            myConn.close();
        }
    }
    
    
    
    /*====================================================
    * test the connection by selecting all from orders table
    */
    public void testConnection() throws SQLException{
        
        try{
            //2. Create a statement
            myStmt = myConn.createStatement();
            
            
            //3. Execute SQL query
            myRs = myStmt.executeQuery("select * from orders");
            
            //4. Get Table Schema
            myRsMD = myRs.getMetaData();
            int noOfCoulumns = myRsMD.getColumnCount();
            int count =1;
            while (count < noOfCoulumns){
                System.out.print (myRsMD.getColumnName(count) + "\t");
                count++;
            }
            System.out.println();
            //4. Process the result set
            myRs = myStmt.executeQuery("select * from orders");
            while(myRs.next()){
                System.out.println(myRs.getString("OrderID") + "," +
                        myRs.getString("UserID") + "," +
                        myRs.getString("Price"));
            }
            
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

