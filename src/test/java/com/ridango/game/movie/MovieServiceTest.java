package com.ridango.game.movie;

import com.github.javafaker.Faker;
import com.ridango.game.model.Movie;
import com.ridango.game.repository.MovieRepository;
import com.ridango.game.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;
    private MovieService underTest;

    private final Faker faker = new Faker();

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

    @Test
    void canAddMovie() {
        String original_title = String.format(String.valueOf(faker.name().title()));
        String overview = String.format(String.valueOf(faker.name().title()));
        BigDecimal popularity = BigDecimal.valueOf(randomDouble(3,1,100));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = Date.valueOf(sdf.format(faker.date().birthday()));
        BigInteger revenue = BigInteger.valueOf((long) randomDouble(10,1,1000000000));
        BigDecimal runtime = BigDecimal.valueOf(randomDouble(3,60,250));
        String tagline = String.format(String.valueOf(faker.name().title()));
        String title = String.format(String.valueOf(faker.name().title()));
        BigDecimal voteaverage = BigDecimal.valueOf(randomDouble(3,60,250));
        Integer votecount = ((int) randomDouble(3,1,100));
        // given
        Movie movie = new Movie(
                original_title,
                overview,
                popularity,
                date,
                revenue,
                runtime,
                tagline,
                title,
                voteaverage,
                votecount

        );

        // when
        underTest.addMovie(movie);

        // then
        ArgumentCaptor<Movie> movieArgumentCaptor =
                ArgumentCaptor.forClass(Movie.class);

        verify(movieRepository)
                .save(movieArgumentCaptor.capture());

        Movie capturedMovie = movieArgumentCaptor.getValue();

        assertThat(capturedMovie).isEqualTo(movie);
    }

    private BigDecimal decimalBetween(long min, long max) {
        if (min == max) {
            return new BigDecimal(min);
        }
        final long trueMin = Math.min(min, max);
        final long trueMax = Math.max(min, max);

        final double range = (double) trueMax - (double) trueMin;

        final double chunkCount = Math.sqrt(Math.abs(range));
        final double chunkSize = chunkCount;
        final long randomChunk = faker.random().nextLong((long) chunkCount);

        final double chunkStart = trueMin + randomChunk * chunkSize;
        final double adj = chunkSize * faker.random().nextDouble();
        return new BigDecimal(chunkStart + adj);
    }

    private double randomDouble(int maxNumberOfDecimals, long min, long max) {
        return decimalBetween(min,max)
                .setScale(maxNumberOfDecimals, BigDecimal.ROUND_HALF_DOWN)
                .doubleValue();
    }

}