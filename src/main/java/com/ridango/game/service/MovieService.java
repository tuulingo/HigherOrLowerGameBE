package com.ridango.game.service;

import com.ridango.game.model.Movie;
import com.ridango.game.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findMovieById(BigInteger id){
        boolean exists = movieRepository.existsById(id);
        if(exists){
            return movieRepository.findById(id);
        }
        throw new IllegalStateException("movie with id " + id + " does not exist");
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }


}
