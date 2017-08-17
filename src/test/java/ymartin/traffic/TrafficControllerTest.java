package ymartin.traffic;


import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrafficControllerTest {

    public static final Duration GREEN_DURATION = Duration.ofMinutes(4).plusSeconds(30);
    public static final Duration YELLOW_DURATION = Duration.ofSeconds(30);

    private TrafficController trafficController;
    private StubSystemTime systemTime;
    private MockTrafficView mockTrafficView;
    private FakeScheduledExecutorService fakeExecutorService;

    @Before
    public void setUp() throws Exception {
        systemTime = new StubSystemTime(LocalDateTime.now());
        mockTrafficView = new MockTrafficView();
        fakeExecutorService = new FakeScheduledExecutorService();
        trafficController = new TrafficController(
                mockTrafficView, fakeExecutorService, systemTime, Duration.ofMinutes(30), GREEN_DURATION, YELLOW_DURATION
        );
    }

    @Test
    public void shouldShutdownSimulation() throws InterruptedException {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(30).plusSeconds(0));

        assertThat(fakeExecutorService.isShutdown(), is(true));
    }

    @Test
    public void shouldReturnCurrentSystemTime() throws InterruptedException {
        trafficController.simulate();

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getDateTime(), is(systemTime.now()));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedInitially() throws InterruptedException {
        trafficController.simulate();

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter4Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(GREEN_DURATION);

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsGreenAfter5Min0Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION);

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.GREEN));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsYellowAfter9Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION);

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.YELLOW));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedAfter10Min0Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION, YELLOW_DURATION);

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter14Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION);

        IntersectionState state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }
}