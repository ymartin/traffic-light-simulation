package ymartin.traffic;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Intersection {

    private ScheduledExecutorService scheduledExecutorService;
    private IntersectionView intersectionView;
    private SystemTime systemTime;
    private Duration shutdownDuration;
    private IntersectionTrafficState intersectionTrafficState;
    private Map<IntersectionTrafficState, Duration> durationMap;

    public interface IntersectionView {
        void render(IntersectionSnapshot intersectionSnapshot);
    }

    public Intersection(IntersectionView intersectionView, ScheduledExecutorService scheduledExecutorService,
                        SystemTime systemTime, Duration shutdownDuration, Duration greenDuration, Duration yellowDuration) {
        this.intersectionView = intersectionView;
        this.scheduledExecutorService = scheduledExecutorService;
        this.systemTime = systemTime;
        this.shutdownDuration = shutdownDuration;
        durationMap = new HashMap<>();
        durationMap.put(IntersectionTrafficState.GREEN_RED, greenDuration);
        durationMap.put(IntersectionTrafficState.RED_GREEN, greenDuration);
        durationMap.put(IntersectionTrafficState.YELLOW_RED, yellowDuration);
        durationMap.put(IntersectionTrafficState.RED_YELLOW, yellowDuration);
    }

    public void run() {
        scheduledExecutorService.schedule((Runnable) scheduledExecutorService::shutdownNow, shutdownDuration.toMillis(), TimeUnit.MILLISECONDS);
        intersectionTrafficState = IntersectionTrafficState.RED_YELLOW;
        updateStateAndRender();
    }

    private void updateStateAndRender() {
        this.intersectionTrafficState = intersectionTrafficState.nextState;
        intersectionView.render(new IntersectionSnapshot(
                new TrafficLight(intersectionTrafficState.northSouthColour), new TrafficLight(intersectionTrafficState.northSouthColour),
                new TrafficLight(intersectionTrafficState.eastWestColour), new TrafficLight(intersectionTrafficState.eastWestColour),
                systemTime.now()));
        scheduledExecutorService.schedule(this::updateStateAndRender, durationMap.get(intersectionTrafficState).toMillis(), TimeUnit.MILLISECONDS);
    }

    private enum IntersectionTrafficState {
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
