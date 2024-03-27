package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;
import sg.edu.nus.iss.ibfb4ssfassessment.repo.MovieRepo;

@Service
public class DatabaseService {

    @Autowired
    MovieRepo repo;

    // Task 2 (Save to Redis Map)
    public void saveRecord(Movie movie) {
        repo.createMovie(String.valueOf(movie.getMovieId()), movieToString(movie));
    }

    private String movieToString(Movie movie) {
        return Json.createObjectBuilder()
                .add("movieId", movie.getMovieId())
                .add("title", movie.getTitle())
                .add("year", movie.getYear())
                .add("rated", movie.getRated())
                .add("releaseDate", movie.getReleaseDate())
                .add("runTime", movie.getRunTime())
                .add("genre", movie.getGenre())
                .add("director", movie.getDirector())
                .add("rating", movie.getRating())
                .add("formattedReleaseDate", movie.getFormattedReleaseDate().toString())
                .add("count", movie.getCount())
                .build().toString();
    }

    // Task 3 (Map or List - comment where necesary)
    public long getNumberOfMovies() {
        return repo.getNumberOfMovies();
    }

    // Task 4 (Map)
    public Movie getMovieById(Integer movieId) {
        String movieStr = repo.retrieveMovie(String.valueOf(movieId));
        return stringToMovie(movieStr);
    }

    private Movie stringToMovie(String movieStr) {
        JsonReader reader = Json.createReader(new StringReader(movieStr));
        JsonObject jMovie = reader.readObject();
        Movie movie = new Movie();
        movie.setMovieId(jMovie.getInt("movieId"));
        movie.setTitle(jMovie.getString("title"));
        movie.setYear(jMovie.getString("year"));
        movie.setRated(jMovie.getString("rated"));
        Long releaseDate = jMovie.getJsonNumber("releaseDate").longValueExact();
        movie.setReleaseDate(releaseDate);
        movie.setRunTime(jMovie.getString("runTime"));
        movie.setGenre(jMovie.getString("genre"));
        movie.setDirector(jMovie.getString("director"));
        movie.setRating(jMovie.getJsonNumber("rating").doubleValue());
        movie.setCount(jMovie.getInt("count"));
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy");
        Date date;
        try {
            date = sdf.parse(jMovie.getString("formattedReleaseDate"));
        } catch (ParseException e) {
            e.printStackTrace();
            date = new Date(releaseDate);
        }
        movie.setFormattedReleaseDate(date);
        return movie;
    }

    // Task 5
    public List<Movie> getAllMovies() {
        return repo.retrieveAllMovies().stream().map(movieStr -> stringToMovie(movieStr)).toList();
    }

}
