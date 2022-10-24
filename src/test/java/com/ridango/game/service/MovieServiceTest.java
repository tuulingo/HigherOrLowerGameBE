package com.ridango.game.service;

import com.ridango.game.model.Movie;
import com.ridango.game.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.math.BigInteger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MovieService(movieRepository);
    }

    @Test
    void canFindAllMovies() {
        underTest.findAllMovies();
        verify(movieRepository).findAll();
    }

    @Test
    void willThrowWhenFindMovieNotFound() {
        BigInteger id = BigInteger.valueOf(10);
        given(movieRepository.existsById(id))
                .willReturn(false);

        assertThatThrownBy(() -> underTest.findMovieById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("movie with id " + id + " does not exist");

        verify(movieRepository, never()).findById(any());
    }

    @Test
    void canFindMovieById() {
        BigInteger id = BigInteger.valueOf(10);
        given(movieRepository.existsById(id)).willReturn(true);

        underTest.findMovieById(id);

        verify(movieRepository).findById(id);
    }

}