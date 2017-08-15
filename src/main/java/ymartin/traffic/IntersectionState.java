package ymartin.traffic;

import java.time.LocalDateTime;

public class IntersectionState {
    private Intersection intersection;
    private LocalDateTime dateTime;

    public IntersectionState(Intersection intersection, LocalDateTime dateTime) {
        this.intersection = intersection;
        this.dateTime = dateTime;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
