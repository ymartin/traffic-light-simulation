package ymartin.traffic.intersection;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class Intersection {

    private ScheduledExecutorService scheduledExecutorService;
    private IntersectionListener intersectionListener;
    private SystemTime systemTime;
    private TransitionState transitionState;

    public interface IntersectionListener {
        void notifyChange(IntersectionSnapshot intersectionSnapshot);
    }

    public Intersection(IntersectionListener intersectionListener, ScheduledExecutorService scheduledExecutorService,
                        SystemTime systemTime, Duration greenDuration, Duration yellowDuration) {
        this.intersectionListener = intersectionListener;
        this.scheduledExecutorService = scheduledExecutorService;
        this.systemTime = systemTime;
        TransitionState.GREEN_RED.duration = greenDuration;
        TransitionState.RED_GREEN.duration = greenDuration;
        TransitionState.YELLOW_RED.duration = yellowDuration;
        TransitionState.RED_YELLOW.duration = yellowDuration;
    }

    public void run(Duration shutdownDuration) {
        scheduledExecutorService.schedule(scheduledExecutorService::shutdownNow, shutdownDuration.toMillis(), TimeUnit.MILLISECONDS);
        transitionState = TransitionState.RED_YELLOW;
        updateStateAndRender();
    }

    private void updateStateAndRender() {
        this.transitionState = transitionState.nextState;
        intersectionListener.notifyChange(transitionState.createSnapshot(systemTime));
        scheduledExecutorService.schedule(this::updateStateAndRender, transitionState.duration.toMillis(), TimeUnit.MILLISECONDS);
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
        private Duration duration;

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
