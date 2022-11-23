package easv.mrs.DAL.db;

import easv.mrs.BE.Movie;
import easv.mrs.DAL.IMovieDataAccess;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO_DB implements IMovieDataAccess {

    private MyDatabaseConnector databaseConnector;

    public MovieDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Movie> getAllMovies() throws Exception {
        try(Connection conn = databaseConnector.getConnection())
        {
            String sql = "SELECT * FROM Movie;";

            Statement stmt = conn.createStatement();
            ArrayList<Movie> allMovies = new ArrayList<>();
            ResultSet rs = stmt.executeQuery(sql);

            //loop trough rows from the database result set
            while (rs.next()){

                //Map DB row to movie object
                int id = rs.getInt("Id");
                String title = rs.getString("Title");
                int year = rs.getInt("Year");
                Movie movie = new Movie(id, year, title);
                allMovies.add(movie);
            }
            return allMovies;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not get movies form database", ex);
        }
    }
    public void uploadMovie(){
        String sql = "INSERT INTO Movie (Title, Year) VALUES (?,?);";
        try(Connection conn = databaseConnector.getConnection()){

            PreparedStatement stmt = conn.prepareStatement(sql);

            List<String> lines = null;
            lines = Files.readAllLines(Path.of("data/temp_movies"));
            int i = 1;
            //parse each line
            for (String line : lines) {
                String[] seperatedLine = line.split(",");

                //Map each seoerated line to movie object
                int year = Integer.parseInt(seperatedLine[1]);
                String title = seperatedLine[0];

                //Bind parameters
                stmt.setString(1,title);
                stmt.setInt(2, year);

                // run the specified sql statement
                stmt.executeUpdate();
                System.out.println(i);
                i++;
            }
        }
        catch (Exception e){
        throw new RuntimeException(e);
        }
    }

    public Movie createMovie(String title, int year) throws Exception {

        String sql = "INSERT INTO Movie (Title, Year) VALUES (?,?);";

        try(Connection conn = databaseConnector.getConnection()){
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //Bind parameters
            stmt.setString(1,title);
            stmt.setInt(2, year);

            // run the specified sql statement
            stmt.executeUpdate();

            //Get the generated Id from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if(rs.next()){
                id = rs.getInt(1);
            }

            // create movie object and send up the layers
            Movie movie = new Movie(id, year, title);
            return movie;
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not create movie", ex);
        }

    }

    public void updateMovie(Movie movie) throws Exception {

        try(Connection conn = databaseConnector.getConnection()) {

            String sql = "UPDATE Movie SET Title = ? , Year = ? WHERE Id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            //Bind parameters
            stmt.setString(1, movie.getTitle());
            stmt.setInt(2, movie.getYear());
            stmt.setInt(3, movie.getId());

            stmt.executeUpdate();
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not update movie", ex);
        }
    }

    public void deleteMovie(Movie movie) throws Exception {
        try(Connection conn = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Movie WHERE Id = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, movie.getId());
            stmt.executeUpdate();
        }
        catch (SQLException ex){
                ex.printStackTrace();
                throw new Exception("Could not delete movie", ex);
            }
    }

    public List<Movie> searchMovies(String query) throws Exception {

        //TODO Do this
        throw new UnsupportedOperationException();
    }

}
