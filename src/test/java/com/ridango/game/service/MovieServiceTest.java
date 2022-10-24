package com.ridango.game.service;

import com.ridango.game.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        // when
        underTest.findAllMovies();
        // then
        verify(movieRepository).findAll();
    }

    @Test
    void willThrowWhenFindMovieNotFound() {
        // given
        BigInteger id = BigInteger.valueOf(10);
        given(movieRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.findMovieById(id))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("movie with id " + id + " does not exist");

        verify(movieRepository, never()).findById(any());
    }

    @Test
    void canFindMovieById() {
        // given
        BigInteger id = BigInteger.valueOf(10);
        given(movieRepository.existsById(id)).willReturn(true);

        // when
        underTest.findMovieById(id);

        // then
        verify(movieRepository).findById(id);
    }

}