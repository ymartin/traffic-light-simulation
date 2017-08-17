package ymartin.traffic;

import ymartin.traffic.intersection.Intersection;
import ymartin.traffic.intersection.IntersectionSnapshot;

import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;

public class Simulation implements Intersection.IntersectionView {
    public static void main(String[] args) {
        new Simulation(System.out, Duration.ofSeconds(3), Duration.ofSeconds(1), Duration.ofMillis(30)).run();
    }

    private static final String ROW_FORMAT = "%-11s %-9s %-7s %-7s %-7s %-7s%n";
    private PrintStream printStream;
    private Intersection intersection;

    Simulation(PrintStream printStream, Duration shutdownDuration, Duration greenDuration, Duration yellowDuration) {
        this.printStream = printStream;
        intersection = new Intersection(this, Executors.newSingleThreadScheduledExecutor(), LocalDateTime::now,
                shutdownDuration, greenDuration, yellowDuration);
    }

    void run() {
        printStream.printf(ROW_FORMAT, "Date", "Time", "North", "South", "East", "West");
        intersection.run();
    }

    @Override
    public void render(IntersectionSnapshot intersectionSnapshot) {
        printStream.printf(ROW_FORMAT,
                intersectionSnapshot.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                intersectionSnapshot.getDateTime().format(DateTimeFormatter.ofPattern("hh:mm:ss")),
                intersectionSnapshot.getNorthLight().getColour(), intersectionSnapshot.getSouthLight().getColour(),
                intersectionSnapshot.getEastLight().getColour(), intersectionSnapshot.getWestLight().getColour());
    }
}
