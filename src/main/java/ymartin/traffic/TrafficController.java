package ymartin.traffic;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class TrafficController {

    private ScheduledExecutorService scheduledExecutorService;
    private TrafficView trafficView;
    private SystemTime systemTime;
    private Duration shutdownDuration;
    private IntersectionTrafficState intersectionTrafficState;
    private Map<IntersectionTrafficState, Duration> durationMap;

    public TrafficController(TrafficView trafficView, ScheduledExecutorService scheduledExecutorService,
                             SystemTime systemTime, Duration shutdownDuration, Duration greenDuration, Duration yellowDuration) {
        this.trafficView = trafficView;
        this.scheduledExecutorService = scheduledExecutorService;
        this.systemTime = systemTime;
        this.shutdownDuration = shutdownDuration;
        durationMap = new HashMap<>();
        durationMap.put(IntersectionTrafficState.GREEN_RED, greenDuration);
        durationMap.put(IntersectionTrafficState.RED_GREEN, greenDuration);
        durationMap.put(IntersectionTrafficState.YELLOW_RED, yellowDuration);
        durationMap.put(IntersectionTrafficState.RED_YELLOW, yellowDuration);
    }

    public void simulate() {
        scheduledExecutorService.schedule((Runnable) scheduledExecutorService::shutdownNow, shutdownDuration.toMillis(), TimeUnit.MILLISECONDS);
        intersectionTrafficState = IntersectionTrafficState.RED_YELLOW;
        updateStateAndRender();
    }

    public void updateStateAndRender() {
        this.intersectionTrafficState = intersectionTrafficState.nextState;
        trafficView.intersectionChange(new IntersectionState(new Intersection(
                new TrafficLight(intersectionTrafficState.northSouthColour), new TrafficLight(intersectionTrafficState.northSouthColour),
                new TrafficLight(intersectionTrafficState.eastWestColour), new TrafficLight(intersectionTrafficState.eastWestColour)
        ), systemTime.now()));
        scheduledExecutorService.schedule(this::updateStateAndRender, durationMap.get(intersectionTrafficState).toMillis(), TimeUnit.MILLISECONDS);
    }

    public interface TrafficView {
        void intersectionChange(IntersectionState intersectionState);
    }

    public enum IntersectionTrafficState {
        GREEN_RED(TrafficLight.Colour.GREEN, TrafficLight.Colour.RED),
        YELLOW_RED(TrafficLight.Colour.YELLOW, TrafficLight.Colour.RED),
        RED_GREEN(TrafficLight.Colour.RED, TrafficLight.Colour.GREEN),
        RED_YELLOW(TrafficLight.Colour.RED, TrafficLight.Colour.YELLOW);

        static {
            GREEN_RED.nextState = YELLOW_RED;
            YELLOW_RED.nextState = RED_GREEN;
            RED_GREEN.nextState = RED_YELLOW;
            RED_YELLOW.nextState = GREEN_RED;
        }

        private final TrafficLight.Colour northSouthColour;
        private final TrafficLight.Colour eastWestColour;
        private IntersectionTrafficState nextState;

        IntersectionTrafficState(TrafficLight.Colour northSouthColour, TrafficLight.Colour eastWestColour) {
            this.northSouthColour = northSouthColour;
            this.eastWestColour = eastWestColour;
        }
    }
}
