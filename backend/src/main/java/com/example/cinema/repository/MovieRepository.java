package com.example.cinema.repository;

import com.example.cinema.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByDeletedFalse();

    Optional<Movie> findByIdAndDeletedFalse(Long id);

    Optional<Movie> findByNameAndDeletedFalse(String name);

    @Modifying
    @Query("""
            UPDATE Movie m
            SET m.deleted = true
            WHERE m.id = :id
            """)
    void deleteLogically(@Param("id") Long id);
}
