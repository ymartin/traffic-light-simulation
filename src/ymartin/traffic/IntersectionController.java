package ymartin.traffic;

import java.time.DateTimeException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class IntersectionController {

    private final Duration yellowDuration;
    private SystemTime systemTime;

    public IntersectionController(SystemTime systemTime, Duration yellowDuration) {
        this.systemTime = systemTime;
        this.yellowDuration = yellowDuration;
    }

    public List<IntersectionState> simulate() {
        return Arrays.asList(
                new IntersectionState(new Intersection(
                        new TrafficLight(TrafficLight.Color.GREEN), new TrafficLight(TrafficLight.Color.GREEN),
                        new TrafficLight(TrafficLight.Color.RED), new TrafficLight(TrafficLight.Color.RED)
                ), systemTime.now()),
                new IntersectionState(new Intersection(
                        new TrafficLight(TrafficLight.Color.YELLOW), new TrafficLight(TrafficLight.Color.YELLOW),
                        new TrafficLight(TrafficLight.Color.RED), new TrafficLight(TrafficLight.Color.RED)
                ), systemTime.now().plus(yellowDuration))
        );
    }

}
