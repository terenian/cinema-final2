/*
* This class represents a Movie item in the Movies Table.
*/
package EntitiesLayer;

/**
 *
 * @author Eran Z. & Itzik W.
 */
public class Movie {
    
    
    private Integer movieID;
    private String movieName;
    private Integer movieLength;

    
    public Movie(Integer id, String name, Integer length){
        this.movieID = id;
        this.movieName = name;
        this.movieLength = length;
    }
    
    public Integer getMovieId() {
        return movieID;
    }
    
    public String getMovieName() {
        return movieName;
    }
    
    public Integer getMovieLength() {
        return movieLength;
    }
    
  
    public String toString(){
        return  "[" + movieID+" , "+
                movieName+" , "+
                movieLength + "]";
    }
   
    
}

