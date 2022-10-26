package com.ridango.game.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ridango.game.model.Movie;
import com.ridango.game.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class MovieIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieRepository movieRepository;

    private final Faker faker = new Faker();

    @Test
    void canGetAllMovies() {
    }

    @Test
    void canGetMovie() throws Exception {
        // given
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

        mockMvc.perform(post("/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk());

        MvcResult getMoviesResult = mockMvc.perform(get("/movie/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = getMoviesResult
                .getResponse()
                .getContentAsString();

        List<Movie> movies = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {
                }
        );

        BigInteger id = movies.stream()
                .filter(m -> m.equals(movie.getORIGINAL_TITLE()))
                .map(Movie::getID)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                  "movie with title " + original_title + " does not exist"));

        // when
        ResultActions resultActions = mockMvc
                .perform(get("/movie/find/" + id));

        // then
        resultActions.andExpect(status().isOk());
        boolean exists = movieRepository.existsById(id);
        assertThat(exists).isFalse();
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