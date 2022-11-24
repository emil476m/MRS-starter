package easv.mrs.BE;

public class Rating {
    int userId;
    int movieId;
    int rating;

    public Rating(int userId, int movieId, int rating){
        this.rating = rating;
        this.movieId = movieId;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
    @Override
    public String toString() {
        return "userId=" + userId + ", movieId=" + movieId + ", rating=" + rating;
    }
}
