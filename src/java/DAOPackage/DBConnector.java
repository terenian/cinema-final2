package DAOPackage;
import java.sql.*;
import java.util.logging.Level;
/**
 * This class establishes a connection with the DB using MySQL Connector.
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
    /**
     * gets db name, username and passwords and creates a connection.
     * @param c Configuration file
     * @throws SQLException
     */
    
    public DBConnector(Configuration c) throws SQLException {
        
        user = c.getUserName();
        pass = c.getPassword();
        host = c.getDbServer();
        dbName = c.getDbName();        
        
        connectionString = "jdbc:mysql://"+ 
                host +"/" + 
                dbName + "?useSSL=false&useUnicode=true&serverTimezone=Asia/Jerusalem";
        try{
            Class.forName("com.mysql.jdbc.Driver");  
            myConn = DriverManager.getConnection(connectionString, user, pass);
            connectionIsOn = true;
            
            System.out.println(this.getClass().toString() + ": CONNECTION SUCCEEDED");

        }
        catch (Exception e){
            System.out.println(this.getClass().toString() + ": CONNECTION FAILED");
            CinemaLogger.log(Level.SEVERE, this.getClass() + e.getMessage());
        }
        
        
    }
    
    /**
     * @return a connection for the DB
     */
    public Connection getConnection (){
        if (connectionIsOn){
            return myConn;
        }
        return null;
    }


    /**
     *Close the connection
     * @throws SQLException
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
    
    
    
   
    /**
     *Tester
     * @throws SQLException
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

