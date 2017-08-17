package ymartin.traffic.intersection;

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
    private TransitionState transitionState;
    private Map<TransitionState, Duration> durationMap;

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
        durationMap.put(TransitionState.GREEN_RED, greenDuration);
        durationMap.put(TransitionState.RED_GREEN, greenDuration);
        durationMap.put(TransitionState.YELLOW_RED, yellowDuration);
        durationMap.put(TransitionState.RED_YELLOW, yellowDuration);
    }

    public void run() {
        scheduledExecutorService.schedule(scheduledExecutorService::shutdownNow, shutdownDuration.toMillis(), TimeUnit.MILLISECONDS);
        transitionState = TransitionState.RED_YELLOW;
        updateStateAndRender();
    }

    private void updateStateAndRender() {
        this.transitionState = transitionState.nextState;
        intersectionView.render(transitionState.createSnapshot(systemTime));
        scheduledExecutorService.schedule(this::updateStateAndRender, durationMap.get(transitionState).toMillis(), TimeUnit.MILLISECONDS);
    }

    private enum TransitionState {
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
        private TransitionState nextState;

        TransitionState(TrafficLight.Colour northSouthColour, TrafficLight.Colour eastWestColour) {
            this.northSouthColour = northSouthColour;
            this.eastWestColour = eastWestColour;
        }

        IntersectionSnapshot createSnapshot(SystemTime systemTime) {
            return new IntersectionSnapshot(
                    new TrafficLight(northSouthColour), new TrafficLight(northSouthColour),
                    new TrafficLight(eastWestColour), new TrafficLight(eastWestColour),
                    systemTime.now());
        }
    }
}
