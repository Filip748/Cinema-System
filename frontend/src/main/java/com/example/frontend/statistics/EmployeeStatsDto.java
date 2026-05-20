package com.example.frontend.statistics;

public class EmployeeStatsDto {
    private String employeeName;
    private long ticketsSold;

    public EmployeeStatsDto() {}

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public long getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(long ticketsSold) {
        this.ticketsSold = ticketsSold;
    }
}
