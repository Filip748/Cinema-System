package com.example.backend.HallCreator.service;

import org.springframework.transaction.annotation.Transactional;
import com.example.backend.HallCreator.dto.SeatRequest;
import com.example.backend.HallCreator.model.CinemaHall;
import com.example.backend.HallCreator.model.Seat;
import com.example.backend.HallCreator.repository.CinemaHallRepository;
import com.example.backend.HallCreator.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CinemaHallCreator {

    private final CinemaHallRepository cinemaHallRepository;
    private final SeatRepository seatRepository;

    public CinemaHallCreator(CinemaHallRepository cinemaHallRepository, SeatRepository seatRepository){
        this.cinemaHallRepository = cinemaHallRepository;
        this.seatRepository = seatRepository;
    }

    public void generateSeats(Long hallId, List<SeatRequest> selectedSeats) {
        CinemaHall hall = cinemaHallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("CinemaHall: " + hallId + " not found"));


        for (SeatRequest selected : selectedSeats) {
            Seat newSeat = new Seat();
            newSeat.setRowNumber(selected.getRow());
            newSeat.setSeatNumber(selected.getSeat());
            newSeat.setCinemaHall(hall);

            seatRepository.save(newSeat);
            }
            hall.setCapacity(selectedSeats.size());
            cinemaHallRepository.save(hall);
    }

    @Transactional
    public void deleteHall(Long hallId) {
        CinemaHall hall = cinemaHallRepository.findById(hallId)
                .orElseThrow(() -> new RuntimeException("CinemaHall: " + hallId + " not found"));

        seatRepository.deleteByCinemaHallId(hallId);
        cinemaHallRepository.delete(hall);
    }

}


