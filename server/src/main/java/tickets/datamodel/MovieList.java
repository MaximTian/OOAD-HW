package src.main.java.tickets.datamodel;

import java.util.ArrayList;

public class MovieList {
    private ArrayList<Movie> movieList;

    public MovieList() {
        movieList = new ArrayList<Movie>();
    }
    
    public void setmovieList(ArrayList<Movie> list) {
        movieList = list;
    }
    
    public ArrayList<Movie> getMovieList() {
        return movieList;
    }
    
    public void add(Movie movie) {
        movieList.add(movie);
    }
    
    public int size() {
        return movieList.size();
    }
}
