/*
*
*/
package DAOPackage;
import java.io.File;
import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import PathToXml.PathToXML;

public class Configuration {
    
    
    private String dbServer;
    private String userName;
    private String password;
    private String logPath;
    private String dbName;
    //public final static String PATH_TO_XML = "C:\\NetBeansProjects\\Cinema\\web\\CinemaTicketsConfig.xml";
    public Configuration() throws Throwable{
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        
        //System.out.println ("========TEST========== " +  getClass().getResourceAsStream("/DAOPackage/CinemaTicketsConfig.xml"));
        InputStream is = getClass().getResourceAsStream("/DAOPackage/CinemaTicketsConfig.xml");
        
        //File f = new File(PATH_TO_XML);
        //File f = new File(getClass().getClassLoader().getResource("CinemaTicketsConfig.xml").getPath());
        
        //Document doc = db.parse(f);
        Document doc = db.parse(is);
        
        //NodeList l = doc.getElementsByTagName("Config");
        Element config = doc.getDocumentElement();
        this.dbServer = config.getElementsByTagName("DBServer").item(0).getTextContent();
        this.userName = config.getElementsByTagName("UserName").item(0).getTextContent();
        this.password = config.getElementsByTagName("Password").item(0).getTextContent();
        this.logPath = config.getElementsByTagName("LogPath").item(0).getTextContent();
        this.dbName = config.getElementsByTagName("DbName").item(0).getTextContent();
    }
    
    public String getDbServer() {
        return dbServer;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getLogPath() {
        return logPath;
    }
    
    public String getDbName() {
        return dbName;
    }
    
    
}
