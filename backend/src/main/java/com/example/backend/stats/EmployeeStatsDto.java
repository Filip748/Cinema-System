package com.example.backend.stats;

public class EmployeeStatsDto {
    private String employeeName;
    private long ticketsSold;

    public EmployeeStatsDto() {}

    public EmployeeStatsDto(String employeeName, long ticketsSold) {
        this.employeeName = employeeName;
        this.ticketsSold = ticketsSold;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public long getTicketsSold() {
        return ticketsSold;
    }
}
