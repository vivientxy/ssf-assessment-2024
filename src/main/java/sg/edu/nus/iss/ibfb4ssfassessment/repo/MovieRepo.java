package sg.edu.nus.iss.ibfb4ssfassessment.repo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.ibfb4ssfassessment.util.Utils;

@Repository
public class MovieRepo {

    @Autowired @Qualifier(Utils.REDIS_STRING)
    RedisTemplate<String,String> redisTemplate;

    HashOperations<String,String,String> hashOps;

    // CRUD
    public void createMovie(String movieId, String movie) {
        hashOps = redisTemplate.opsForHash();
        hashOps.put(Utils.MOVIE_DB, movieId, movie);
    }

    public String retrieveMovie(String movieId) {
        hashOps = redisTemplate.opsForHash();
        return hashOps.get(Utils.MOVIE_DB, movieId);
    }

    public List<String> retrieveAllMovies() {
        hashOps = redisTemplate.opsForHash();
        return hashOps.values(Utils.MOVIE_DB);
    }

    public Long getNumberOfMovies() {
        hashOps = redisTemplate.opsForHash();
        return hashOps.size(Utils.MOVIE_DB);
    }

    public void updateMovie(String movieId, String movie) {
        hashOps = redisTemplate.opsForHash();
        hashOps.put(Utils.MOVIE_DB, movieId, movie);
    }

    public void deleteMovie(String movieId) {
        hashOps = redisTemplate.opsForHash();
        hashOps.delete(Utils.MOVIE_DB, movieId);
    }

}
