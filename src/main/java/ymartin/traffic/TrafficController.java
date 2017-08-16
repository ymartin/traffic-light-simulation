package ymartin.traffic;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


public class TrafficController {

    private final Duration yellowDuration;
    private final Duration greenDuration;
    private ScheduledExecutorService scheduledExecutorService;
    private TrafficView trafficView;
    private SystemTime systemTime;

    public TrafficController(TrafficView trafficView, ScheduledExecutorService scheduledExecutorService,
                             SystemTime systemTime, Duration greenDuration, Duration yellowDuration) {
        this.trafficView = trafficView;
        this.scheduledExecutorService = scheduledExecutorService;
        this.systemTime = systemTime;
        this.greenDuration = greenDuration;
        this.yellowDuration = yellowDuration;
    }

    public void simulate() {
        trafficView.intersectionChange(new IntersectionState(new Intersection(
                new TrafficLight(TrafficLight.Color.GREEN), new TrafficLight(TrafficLight.Color.GREEN),
                new TrafficLight(TrafficLight.Color.RED), new TrafficLight(TrafficLight.Color.RED)
        ), systemTime.now()));

        scheduledExecutorService.schedule(() -> trafficView.intersectionChange(new IntersectionState(new Intersection(
                new TrafficLight(TrafficLight.Color.YELLOW), new TrafficLight(TrafficLight.Color.YELLOW),
                new TrafficLight(TrafficLight.Color.RED), new TrafficLight(TrafficLight.Color.RED)
        ), systemTime.now().plus(greenDuration))), greenDuration.toMillis(), TimeUnit.MILLISECONDS);

        scheduledExecutorService.schedule(() -> trafficView.intersectionChange(new IntersectionState(new Intersection(
                new TrafficLight(TrafficLight.Color.RED), new TrafficLight(TrafficLight.Color.RED),
                new TrafficLight(TrafficLight.Color.GREEN), new TrafficLight(TrafficLight.Color.GREEN)
        ), systemTime.now().plus(greenDuration).plus(yellowDuration))), greenDuration.plus(yellowDuration).toMillis(), TimeUnit.MILLISECONDS);
    }

    public interface TrafficView {
        void intersectionChange(IntersectionState intersectionState);
    }
}
