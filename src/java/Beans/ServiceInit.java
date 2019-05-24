package Beans;

import DAOPackage.*;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;


/**
 *Initiating Services on startup.
 * Initiate DB Access and reading configuration file
 * Creates an instance of all static Services that provides DB access.
 * @author Eran.z & Itzik W.
 */
@Startup
@Singleton
public class ServiceInit {
    private static String status;
    private static DBConnector dbcon;
    public static Configuration conf;
    
    public static OrdersService os;
    public static MoviesService ms;
    public static TicketsService ts;
    public static ScreeningsService ss;
    public static UsersService us;
    public static HallsService hs;
    public static ReviewsService rs;
    
    /*
    service initiation
    */
    public ServiceInit(){
        
    }
    
    @PostConstruct
    public void onStartup() {
        this.status = this.getClass().toString() + ": Initialization started.";
        try {
            //create a connection to the DB and services for all the tables
            conf = new Configuration();
            dbcon = new DBConnector (conf);
        }
        catch (Throwable ex) {
            this.status =  this.getClass().toString() + ": Initialization Failed.";
        }
        System.out.println(status);
    }
 
        //generate services that allow DB access for each table in the DB
        
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