package com.example.backend.ticketSales;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.backend.schedule.Movie;
import com.example.backend.model.Employee;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{
    List<Ticket> findByScreening_Id(Long screeningId);

    long countByEmployee(Employee employee);
    long countByScreening_Movie(Movie movie);
}
