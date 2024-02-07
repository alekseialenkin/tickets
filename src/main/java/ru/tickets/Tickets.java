package ru.tickets;

import java.util.ArrayList;
import java.util.List;

public class Tickets {
    private List<Ticket> tickets = new ArrayList<>();

    public Tickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "Tickets{" +
                "tickets=" + tickets +
                '}';
    }
}
