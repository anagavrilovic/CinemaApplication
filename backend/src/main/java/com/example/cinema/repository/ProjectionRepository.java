package com.example.cinema.repository;

import com.example.cinema.model.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectionRepository extends JpaRepository<Projection, Long> {

    List<Projection> findByTheaterIdAndDeletedFalse(Long theaterId);

    Optional<Projection> findByIdAndDeletedFalse(Long id);

    @Modifying
    @Query("""
            UPDATE Projection p
            SET p.deleted = true
            WHERE p.id = :id
            """)
    void deleteLogically(@Param("id") Long id);

    @Query("""
            SELECT p
            FROM Projection p
            WHERE p.deleted = False and p.numberOfAvailableSeats > 0
            """)
    List<Projection> findAllAvailable();

}
