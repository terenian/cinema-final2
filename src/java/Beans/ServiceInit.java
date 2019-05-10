package Beans;

import DAOPackage.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;


/**
 *
 * @author eran.z & Itzik W.
 */
@Startup
@Singleton
public class ServiceInit {
    private static String status;
    private static DBConnector dbcon;
    private static FileHandler fh;
    private static Configuration conf;
    
    public static OrdersService os;
    public static MoviesService ms;
    public static TicketsService ts;
    public static ScreeningsService ss;
    public static UsersService us;
    public static HallsService hs;
    public static ReviewsService rs;
    static Logger logger;

    static Object reviewsService() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
    service initiation
    */
    public ServiceInit(){
        
    }
    
    @PostConstruct
    public void onStartup() {
        this.status = this.getClass().toString() + ": Initialization started.";
        System.out.println(status);
        try {
            //create a connection to the DB and services for all the tables
            conf = new Configuration();
            dbcon = new DBConnector (conf);
        }
        catch (Throwable ex) {
            this.status = this.getClass().toString() + ": Initialization failed.";
        }
        System.out.println(status);
        //startLogger ();
    }
    
    public void startLogger () {
        try {
            logger = Logger.getLogger("CinemaLog");
            //FileHandler fh;
            fh = new FileHandler(conf.getLogPath(), true);
            
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            
            //Hook to close all fileHandler on close
            final FileHandler fhf = fh;
            Runtime.getRuntime().addShutdownHook(
                    new Thread( () -> {
                        logger.removeHandler(fhf);
                        fhf.close();
                    }));
            
            logger.info(status);
            fh.close();
        } catch (Throwable ex) {
            Logger.getLogger(ServiceInit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static synchronized Logger getLogger(){
        return logger;
    }
    
    //generate services
    
    public static synchronized OrdersService orderService(){
        os = new OrdersService (dbcon);
        return os;
    }
    
    public static synchronized MoviesService moviesService(){
        ms = new MoviesService (dbcon);
        return ms;
    }
    
    public static synchronized TicketsService ticketsService(){
        ts = new TicketsService (dbcon);
        return ts;
    }
    
    public static synchronized ScreeningsService screeningsService(){
        ss = new ScreeningsService (dbcon);
        return ss;
    }
    
    public static synchronized UsersService UsersService(){
        us = new UsersService (dbcon);
        return us;
    }
    
    public static synchronized HallsService hallsService(){
        hs = new HallsService (dbcon);
        return hs;
    }
    
    public static synchronized ReviewsService reviewsServiece(){
        rs = new ReviewsService (dbcon);
        return rs;
    }
    
}