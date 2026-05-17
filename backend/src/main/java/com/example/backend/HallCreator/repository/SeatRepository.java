package com.example.backend.HallCreator.repository;
import org.springframework.transaction.annotation.Transactional;
import com.example.backend.HallCreator.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SeatRepository extends JpaRepository<Seat, Long>{

    @Transactional
    void deleteByCinemaHallId(Long cinemaHallId);
}
