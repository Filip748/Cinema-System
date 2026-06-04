package com.example.backend.schedule;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query("SELECT s FROM Screening s WHERE s.cinemaHall.id = :hallId " +
            "AND s.startTime < :endTime AND s.endTime > :startTime")
    List<Screening> findOverLappingScreenings(
            @Param("hallId") Long hallId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    @Query("SELECT DISTINCT s.cinemaHall.id FROM Screening s WHERE s.startTime < :endTime AND s.endTime > :startTime")
    List<Long> findOccupiedRooms(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );
}
