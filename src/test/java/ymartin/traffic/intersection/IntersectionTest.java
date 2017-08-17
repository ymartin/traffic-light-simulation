package ymartin.traffic.intersection;


import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import ymartin.traffic.util.FakeScheduledExecutorService;
import ymartin.traffic.util.MockIntersectionView;
import ymartin.traffic.util.StubSystemTime;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IntersectionTest {

    public static final Duration GREEN_DURATION = Duration.ofMinutes(4).plusSeconds(30);
    public static final Duration YELLOW_DURATION = Duration.ofSeconds(30);

    private Intersection intersection;
    private StubSystemTime systemTime;
    private MockIntersectionView mockTrafficView;
    private FakeScheduledExecutorService fakeExecutorService;

    @Before
    public void setUp() throws Exception {
        systemTime = new StubSystemTime(LocalDateTime.now());
        mockTrafficView = new MockIntersectionView();
        fakeExecutorService = new FakeScheduledExecutorService();
        intersection = new Intersection(
                mockTrafficView, fakeExecutorService, systemTime, Duration.ofMinutes(30), GREEN_DURATION, YELLOW_DURATION
        );
    }

    @Test
    public void shouldShutdownSimulation() throws InterruptedException {
        intersection.run();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(30).plusSeconds(0));

        assertThat(fakeExecutorService.isShutdown(), is(true));
    }

    @Test
    public void shouldReturnCurrentSystemTime() throws InterruptedException {
        intersection.run();

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getDateTime(), is(systemTime.now()));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedInitially() throws InterruptedException {
        intersection.run();

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), CoreMatchers.is(TrafficLight.Colour.GREEN));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter4Min30Sec() {
        intersection.run();
        fakeExecutorService.executeScheduled(GREEN_DURATION);

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsGreenAfter5Min0Sec() {
        intersection.run();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION);

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.GREEN));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsYellowAfter9Min30Sec() {
        intersection.run();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION);

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.YELLOW));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedAfter10Min0Sec() {
        intersection.run();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION, YELLOW_DURATION);

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter14Min30Sec() {
        intersection.run();
        fakeExecutorService.executeScheduled(GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION, YELLOW_DURATION, GREEN_DURATION);

        IntersectionSnapshot state = mockTrafficView.getLastIntersectionState();
        assertThat(state.getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }
}