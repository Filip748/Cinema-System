package com.example.frontend.ticketSales;

import java.util.List;

public class TicketDto {
    private Long screeningId;
    private List<Long> seatIds;
    private String customerEmail;
    private Long employeeId;
    public TicketDto() {}
    public TicketDto(Long screeningId, List<Long> seatIds, String customerEmail, Long employeeId) {
        this.screeningId = screeningId;
        this.seatIds = seatIds;
        this.customerEmail = customerEmail;
        this.employeeId = employeeId;
    }

    public Long getScreeningId() { return screeningId; }
    public void setScreeningId(Long screeningId) { this.screeningId = screeningId; }
    public List<Long> getSeatIds() { return seatIds; }
    public void setSeatIds(List<Long> seatIds) { this.seatIds = seatIds; }
    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }
}