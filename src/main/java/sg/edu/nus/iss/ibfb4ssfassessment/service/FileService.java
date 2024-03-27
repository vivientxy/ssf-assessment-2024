package sg.edu.nus.iss.ibfb4ssfassessment.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import sg.edu.nus.iss.ibfb4ssfassessment.model.Movie;

@Service
public record FileService() {

    // Task 1 -- specifically to read the file provided. NOT to read Redis DB
    public List<Movie> readFile(String fileName) throws FileNotFoundException {
        JsonReader jReader = Json.createReader(new FileReader(fileName));
        JsonArray movies = jReader.readArray();
        List<Movie> movieList = new LinkedList<>();
        for (JsonValue movieValue : movies) {
            JsonObject jMovie = movieValue.asJsonObject();
            Movie movie = new Movie();
            movie.setMovieId(jMovie.getInt("Id"));
            movie.setTitle(jMovie.getString("Title"));
            movie.setYear(jMovie.getString("Year"));
            movie.setRated(jMovie.getString("Rated"));
            Long releaseDate = jMovie.getJsonNumber("Released").longValueExact();
            movie.setReleaseDate(releaseDate);
            movie.setRunTime(jMovie.getString("Runtime"));
            movie.setGenre(jMovie.getString("Genre"));
            movie.setDirector(jMovie.getString("Director"));
            movie.setRating(jMovie.getJsonNumber("Rating").doubleValue());
            movie.setCount(jMovie.getInt("Count"));
            Date formattedDate = new Date(releaseDate);
            movie.setFormattedReleaseDate(formattedDate);
            movieList.add(movie);
        }
        return movieList;
    }

}