package easv.mrs.DAL;

import easv.mrs.BE.Movie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;

public class MovieDAO_Mock implements IMovieDataAccess {

    private static final String MOVIES_FILE = "data/movie_titles.txt";
    private Path pathToFile = Path.of(MOVIES_FILE);


    @Override
    public List<Movie> getAllMovies() throws IOException {
        List<String> lines = Files.readAllLines(pathToFile);
        List<Movie> movies = new ArrayList<>();

        //parse each line
        for (String line : lines){
            String[] seperatedLine = line.split(",");

            //Map each seoerated line to movie object
            int id = Integer.parseInt(seperatedLine[0]);
            int year = Integer.parseInt(seperatedLine[1]);
            String title = seperatedLine[2];

            //create movie object
            Movie newMovie = new Movie(id, year, title);
            movies.add(newMovie);
        }
        movies.sort(Comparator.comparingInt(Movie::getId));
        return movies;
    }

    @Override
    public Movie createMovie(String title, int year) throws Exception {

        int nextId = getNextId();

        String newLine = nextId + "," + year + "," + title;

        //Append new line using NIO

        Files.write(pathToFile, ("\r\n" + newLine).getBytes(), APPEND);

        return new Movie(nextId, year, title);
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

    }

    @Override
    public void deleteMovie(Movie movie) throws Exception {

    }

    private int getNextId() throws IOException{
        List<Movie> movies = getAllMovies();

        Movie lastMovie = movies.get(movies.size()-1);
        return lastMovie.getId() + 1;
    }

}
