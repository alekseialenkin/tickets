package ru.tickets;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class AppMain {

    public static void main(String[] args) {
        try (FileReader reader = new FileReader("tickets.json"); FileWriter fileWriter = new FileWriter("result.txt")) {
            Tickets tickets = JsonParser.parse(reader, Tickets.class);
            fileWriter.write("Задание 1.\n");
            fileWriter.write(findMinFlyTime(tickets));
            fileWriter.write("Задание 2.\n");
            fileWriter.write("Разница между средней ценой и медианой = " + getDifference(tickets));
        } catch (IOException e) {
            throw new RuntimeException("File not found");
        }
    }

    private static int getDifference(Tickets tickets) {
        List<Integer> price = tickets.getTickets()
                .stream()
                .filter(ticket -> ticket.getOrigin().equals("VVO") && ticket.getDestination().equals("TLV"))
                .map(Ticket::getPrice)
                .sorted()
                .collect(Collectors.toList());
        int median = price.size() % 2 != 0 ? price.get(price.size() / 2)
                : (price.get(price.size() / 2) + price.get(price.size() / 2 - 1)) / 2;
        int avg = (int) price.stream()
                .mapToInt(x -> x)
                .average().orElse(0);
        return avg - median;
    }

    private static String findMinFlyTime(Tickets tickets) {
        Map<String, Long> companyFlyTime = new HashMap<>();
        for (Ticket t : tickets.getTickets()) {
            companyFlyTime.put(t.getCarrier(), Long.MAX_VALUE);
        }
        for (Ticket t : tickets.getTickets()) {
            long value = getTimeDifference(t.getDepartureTime(), t.getArrivalTime());
            if (value < companyFlyTime.get(t.getCarrier())) {
                companyFlyTime.put(t.getCarrier(), value);
            }
        }
        StringBuilder st = new StringBuilder();
        for (Map.Entry<String, Long> e : companyFlyTime.entrySet()) {
            long hour = e.getValue() / 3600;
            long min = (e.getValue() % 3600) / 60;
            long sec = e.getValue() % 60;
            st.append("Авиаперевозчик : ").append(e.getKey()).append(", время перелета : ").append(hour).append(" ч. ").append(min)
                    .append(" мин. ").append(sec).append(" с.").append("\n");
        }
        return st.toString();
    }

    private static Long getTimeDifference(String departureTime, String arrivalTime) {
        LocalTime departure = LocalTime.parse(departureTime, DateTimeFormatter.ofPattern("H:mm"));
        LocalTime arrival = LocalTime.parse(arrivalTime);
        return Duration.between(departure, arrival).getSeconds();
    }
}
