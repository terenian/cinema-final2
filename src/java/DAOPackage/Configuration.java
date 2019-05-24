
package DAOPackage;
import java.io.File;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Reads the configuration XML
 * @author Eran Z. & Itzik W.
 */

public class Configuration {
    
    
    private String dbServer;
    private String userName;
    private String password;
    private String logPath;
    private String dbName;
    
    /**
     * Initiate the Configuration Reading the XML and sets local holders for all db log-in and Logging
     * @throws Throwable
     */
    public Configuration() throws Throwable{
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputStream is = getClass().getResourceAsStream("/DAOPackage/CinemaTicketsConfig.xml");
        Document doc = db.parse(is);
        Element config = doc.getDocumentElement();
        this.dbServer = config.getElementsByTagName("DBServer").item(0).getTextContent();
        this.userName = config.getElementsByTagName("UserName").item(0).getTextContent();
        this.password = config.getElementsByTagName("Password").item(0).getTextContent();
        this.logPath = config.getElementsByTagName("LogPath").item(0).getTextContent();
        this.dbName = config.getElementsByTagName("DbName").item(0).getTextContent();
    }
    
    /**
     * @return String of the db server location
     */
    public String getDbServer() {
        return dbServer;
    }
    
    /**
     * @return userName for db access
     */
    public String getUserName() {
        return userName;
    }
    
    /**
     * @return password for db access
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * @return log file path on machine
     */
    public String getLogPath() {
        return logPath;
    }
    
    /**
     * @return String of the db server name
     */
    public String getDbName() {
        return dbName;
    }
    
    
}
