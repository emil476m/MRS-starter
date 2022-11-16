package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private List<Movie> allMovies;

    public List<Movie> getAllMovies() throws IOException {
        allMovies = new ArrayList<>();
        try (FileReader fr = new FileReader(MOVIES_FILE); BufferedReader br = new BufferedReader(fr);){
            String line;
            while ((line = br.readLine())!=null){
                String[] tempArray = line.split(",");
                Movie movie = new Movie(Integer.parseInt(tempArray[0]), Integer.parseInt(tempArray[1]), tempArray[2]);
                allMovies.add(movie);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return allMovies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {
        return null;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }
}