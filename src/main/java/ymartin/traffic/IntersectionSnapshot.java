package ymartin.traffic;

import java.time.LocalDateTime;

public class IntersectionSnapshot {
    private LocalDateTime dateTime;

    private final TrafficLight northLight;
    private final TrafficLight southLight;
    private final TrafficLight eastLight;
    private final TrafficLight westLight;

    public IntersectionSnapshot(TrafficLight northLight, TrafficLight southLight,
                                TrafficLight eastLight, TrafficLight westLight,
                                LocalDateTime dateTime) {
        this.northLight = northLight;
        this.southLight = southLight;
        this.eastLight = eastLight;
        this.westLight = westLight;
        this.dateTime = dateTime;
    }

    public TrafficLight getNorthLight() {
        return northLight;
    }

    public TrafficLight getSouthLight() {
        return southLight;
    }

    public TrafficLight getEastLight() {
        return eastLight;
    }

    public TrafficLight getWestLight() {
        return westLight;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
