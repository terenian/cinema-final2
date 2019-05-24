package DAOPackage;

import static Beans.ServiceInit.conf;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Provides Logging for this class
 * @author Eran Z. & Itzik W.
 */

public class CinemaLogger {
    static Logger logger;
    static Handler fileHandler;
    Formatter plainText;
    
    //Constructor
    private CinemaLogger() throws IOException{
        //instancitate the logger
        logger = Logger.getLogger(CinemaLogger.class.getName());
        //instance the filehandler
        fileHandler = new FileHandler(conf.getLogPath(),true);
        //instance formatter, set formatting, and handler
        plainText = new SimpleFormatter();
        fileHandler.setFormatter(plainText);
        logger.addHandler(fileHandler);
    }
    
    private static Logger getLogger(){
        if(logger == null){
            try {
                new CinemaLogger();
            } catch (IOException e) {
                System.out.println("CinemaLogger: Error in getLogger");
            }
        }
        return logger;
    }
    
    /**
     *Logs an event
     * @param level
     * @param msg
     */
    public static void log(Level level, String msg){
        getLogger().log(level, msg);
        System.out.println(msg);
    }
    
    /**
     * Closes the file Handler
     */
    public static synchronized void close(){
        try{
            fileHandler.close();
        } catch (Exception e) {
        }
        
    }
}