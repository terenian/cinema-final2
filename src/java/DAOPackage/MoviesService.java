
package DAOPackage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.logging.Logger;

import EntitiesLayer.Movie;
import java.util.logging.Level;



/**
 * This class gives a service of CRUD actions on Movies Table.
 * @author Eran Z. & Itzik W
 */
public class MoviesService {
    
    
    private DBConnector dbConnection;
    
    private final String MOVIE_INSERT = "insert into cinema.movies (MovieName, MovieLength) values (?,?)";
    //Changed by Eran 9.4.19 private final String MOVIE_SEARCH = "select * from cinema.movies where MovieID like (?) and MovieName like (?) and MovieLength like (?)";
    private final String MOVIE_SEARCH = "select * from cinema.movies where MovieName like (?) and MovieID like (?)";
    private final String MOVIE_UPDATE = "update cinema.movies set MovieName=(?), set MovieLength=(?) where MovieID like (?)";
    //private final Logger logger = ServiceManager.getLogger();
    
    /**
     *
     * @param dbConnection
     */
    public MoviesService(DBConnector dbConnection){
        this.dbConnection = dbConnection;
    }
    

    /**
     *Inserts a movie
     * @param name
     * @param length
     * @return insertion result as the id of inserted movie
     * @throws SQLException
     */
    
    public Integer insertMovie(String name, Integer length)throws SQLException{
        
        Connection c = dbConnection.getConnection();
        PreparedStatement movieInsertSTM = null;
        movieInsertSTM = c.prepareStatement(MOVIE_INSERT);
        
        movieInsertSTM.setString(1, name);
        movieInsertSTM.setInt(2, length);
        int result = 0;
        movieInsertSTM.executeUpdate();
        if (result >= 0) {
             CinemaLogger.log(Level.INFO, this.getClass() + "Insert Success");
        }
        else{
            CinemaLogger.log(Level.SEVERE, this.getClass() + "Insert Failed");
        }
        return result;
    }
    
    /**
     * Search for a movie by its name
     * @param name
     * @return list of comptible movies
     * @throws SQLException
     */
    
    public ArrayList<Movie> searchMoviesbyName(String name)
            throws SQLException{
        //logger.info("search Movie("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement movieSearchSTM = null;
        movieSearchSTM = c.prepareStatement(MOVIE_SEARCH);
        if (name.equals("")) {
            movieSearchSTM.setString(1, "%");
        }
        else{
            movieSearchSTM.setString(1, name);
        }
        movieSearchSTM.setString(2, "%");
        
       CinemaLogger.log(Level.INFO, this.getClass() + "movieSearchSTM:" + movieSearchSTM.toString());
        
        ResultSet rs = movieSearchSTM.executeQuery();
        ArrayList<Movie> list = new ArrayList<Movie> ();
        while(rs.next()){
            Movie ord = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3));
            list.add(ord);
        }
        return list;
    }
    
    /**
     * @param id
     * @return a Movie record
     * @throws SQLException
     */
    public Movie gethMoviesbyID(Integer id)
            throws SQLException{
        Connection c = dbConnection.getConnection();
        PreparedStatement movieSearchSTM = null;
        movieSearchSTM = c.prepareStatement(MOVIE_SEARCH);
        if (id.equals(0)) {
            movieSearchSTM.setString(1, "%");
            movieSearchSTM.setString(2, "%");
        }
        else{
            movieSearchSTM.setString(1, "%");
            movieSearchSTM.setInt(2, id);
        }
        
        CinemaLogger.log(Level.INFO, this.getClass() + "movieSearchSTM:" + movieSearchSTM.toString());
        
        ResultSet rs = movieSearchSTM.executeQuery();
        ArrayList<Movie> list = new ArrayList<Movie> ();
        while(rs.next()){
            Movie mov = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3));
            list.add(mov);
        }
        CinemaLogger.log(Level.INFO, this.getClass() + "movie found is: " + list.get(0).toString());
        return list.get(0);
    }
    
    /**
     * @param id
     * @return an array list with a movie
     * @throws SQLException
     */
    public ArrayList<Movie> searchMoviesbyID(Integer id)
            throws SQLException{
        //logger.info("search Movie("+id+","+name+","+description+","+parentId+")");
        Connection c = dbConnection.getConnection();
        PreparedStatement movieSearchSTM = null;
        movieSearchSTM = c.prepareStatement(MOVIE_SEARCH);
        if (id.equals(0)) {
            movieSearchSTM.setString(1, "%");
            movieSearchSTM.setString(2, "%");
        }
        else{
            movieSearchSTM.setString(1, "%");
            movieSearchSTM.setInt(2, id);
        }
        
        CinemaLogger.log(Level.INFO, this.getClass() + "movieSearchSTM:" + movieSearchSTM.toString());
        
        ResultSet rs = movieSearchSTM.executeQuery();
        ArrayList<Movie> list = new ArrayList<Movie> ();
        while(rs.next()){
            Movie ord = new Movie(rs.getInt(1), rs.getString(2), rs.getInt(3));
            list.add(ord);
        }
        return list;
    }
    
    /**
     * update movie
     * @param id
     * @param name
     * @param length
     * @return true if update succeed
     * @throws SQLException
     */
    
    public boolean updateMovie(Integer id, String name ,Integer length)
            throws SQLException{
        //logger.info("update Movie("+id+","+name+","+description+","+parentId+")");
        if (id == null) {
            return false;
        }
        Connection c = dbConnection.getConnection();
        PreparedStatement movieUpdateSTM = null;
        movieUpdateSTM = c.prepareStatement(MOVIE_UPDATE);
               
        if (length == null) {
            movieUpdateSTM.setNull(1, java.sql.Types.INTEGER);
        }
        else
        {
            movieUpdateSTM.setInt(1,length);
        }
    
        movieUpdateSTM.setString(2, name);
        
        CinemaLogger.log(Level.INFO, this.getClass() + "movieUpdateSTM:" + movieUpdateSTM.toString());

        Integer i = movieUpdateSTM.executeUpdate();
        if (i>0) {
            return true;
        }
        return false;
    }
    
    /**
     * Deletes a Movie
     * @param movieID
     * @return true if deletion succeed
     * @throws SQLException
     */
    public boolean deleteMovie(int movieID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement movieSearchSTM = null;
        movieSearchSTM = c.prepareStatement("select cinema.movies.MovieID from cinema.movies,cinema.screenings where cinema.movies.MovieID = (?)"
                + "and cinema.movies.MovieID = cinema.screenings.MovieID");
        movieSearchSTM.setInt(1, movieID);
        
        ResultSet rs = movieSearchSTM.executeQuery();
        if(rs.first())
        {
            return false;
        }
        else
        {
            movieSearchSTM = c.prepareStatement("delete from cinema.movies where MovieID = (?)");
            movieSearchSTM.setInt(1, movieID);
            return (movieSearchSTM.executeUpdate()> 0);
        }
    }

    /**
     * adds a movie
     * @param movieName
     * @param movieLength
     * @return true if add succeed
     * @throws SQLException
     */
    public boolean addMovie(String movieName,int movieLength) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("INSERT INTO cinema.Movies (MovieName, MovieLength) VALUES ((?),(?))");
        prepStat.setString(1, movieName);
        prepStat.setInt(2, movieLength);

        return (prepStat.executeUpdate()> 0);
    }

    /**
     * gets a movie Name by the screening id
     * @param screeningID
     * @return string of name if succeeded, empty string if not
     * @throws SQLException
     */
    public String getMovieNameByScreeningID(int screeningID) throws SQLException
    {
        Connection c = dbConnection.getConnection();
        PreparedStatement prepStat = c.prepareStatement("select movies.MovieName from movies, screenings where movies.MovieID = screenings.MovieID "
                + "and screenings.ScreeningID = (?)");
        prepStat.setInt(1, screeningID);
        CinemaLogger.log(Level.INFO, this.getClass() + "prepStat:" + prepStat.toString());
        ResultSet rs = prepStat.executeQuery();
        if(rs.first())
            return rs.getString(1);
        else
            return "";
    }
}
