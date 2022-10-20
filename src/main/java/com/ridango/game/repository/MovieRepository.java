package com.ridango.game.repository;

import com.ridango.game.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface MovieRepository extends JpaRepository<Movie, BigInteger> {
}
