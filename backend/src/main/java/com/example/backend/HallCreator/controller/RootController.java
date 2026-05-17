package com.example.backend.HallCreator.controller;

import com.example.backend.HallCreator.dto.CreatorRequest;
import com.example.backend.HallCreator.model.CinemaHall;
import com.example.backend.HallCreator.repository.CinemaHallRepository;
import com.example.backend.HallCreator.service.CinemaHallCreator;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/root")
public class RootController {
    private final CinemaHallCreator cinemaHallCreator;
    private final CinemaHallRepository cinemaHallRepository;

    public RootController(CinemaHallCreator cinemaHallCreator, CinemaHallRepository cinemaHallRepository){
        this.cinemaHallCreator = cinemaHallCreator;
        this.cinemaHallRepository = cinemaHallRepository;
    }

    //dodanie sali
    @PostMapping("/halls")
    public String addCinemaHall(@RequestBody CinemaHall hall){
        CinemaHall savedHall = cinemaHallRepository.save(hall);
        return "CinemaHall with ID " + savedHall.getId() + " has been created";
    }

    //kreator sali
    @PostMapping("/halls/hallCreator")
    public String hallCreator(@RequestBody CreatorRequest request){
        cinemaHallCreator.generateSeats(request.getHallId(),request.getSelectedSeats());
        int seats = request.getSelectedSeats().size();
        return "CinemaHall has been generated with " + seats + " seats in hall " + request.getHallId();
    }

    //usuwanie sali
    @DeleteMapping("/halls/{id}")
    public String deleteHall(@PathVariable Long id){
        cinemaHallCreator.deleteHall(id);
        return "CinemaHall with ID " + id + " has been deleted";
    }




}
