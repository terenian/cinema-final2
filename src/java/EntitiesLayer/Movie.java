
package EntitiesLayer;

/**
 *This class represents a Movie item in the Movies Table.
 * @author Eran Z. & Itzik W.
 */
public class Movie {
    
    
    private Integer movieID;
    private String movieName;
    private Integer movieLength;

    
    public Movie(){

    }
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
    
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieLength(Integer movieLength) {
        this.movieLength = movieLength;
    }
    
  
    public String toString(){
        return  "[" + movieID+" , "+
                movieName+" , "+
                movieLength + "]";
    }
   
    
}

