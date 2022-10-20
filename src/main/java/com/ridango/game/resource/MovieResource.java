package com.ridango.game.resource;

import com.ridango.game.model.Movie;
import com.ridango.game.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/movie")
public class MovieResource {
    private final MovieService movieService;

    public MovieResource(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Movie>> getAllMovies(){
        List<Movie> movies = movieService.findAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Optional<Movie>> getMovieById(@PathVariable("id") BigInteger id) {
        Optional<Movie> movie = movieService.findMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
}
