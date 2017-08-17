package ymartin.traffic;



import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TrafficControllerTest {

    private TrafficController trafficController;
    private StubSystemTime systemTime;
    private MockTrafficView fakeTrafficUserInterface;
    private FakeScheduledExecutorService fakeExecutorService;

    @Before
    public void setUp() throws Exception {
        systemTime = new StubSystemTime(LocalDateTime.now());
        fakeTrafficUserInterface = new MockTrafficView();
        fakeExecutorService = new FakeScheduledExecutorService();
        trafficController = new TrafficController(
                fakeTrafficUserInterface, fakeExecutorService, systemTime,
                Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30)
        );
    }

    @Test
    public void shouldReturnCurrentSystemTime() throws InterruptedException {
        trafficController.simulate();

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getDateTime(), is(systemTime.now()));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedInitially() throws InterruptedException {
        trafficController.simulate();

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter4Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsGreenAfter5Min0Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.GREEN));
    }

    @Test
    public void shouldReturnNorthSouthAsRedAndEastWestAsYellowAfter9Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.YELLOW));
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedAfter10Min0Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.GREEN));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter14Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30), Duration.ofSeconds(30));
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getIntersection().getNorthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getSouthLight().getColour(), is(TrafficLight.Colour.YELLOW));
        assertThat(state.getIntersection().getEastLight().getColour(), is(TrafficLight.Colour.RED));
        assertThat(state.getIntersection().getWestLight().getColour(), is(TrafficLight.Colour.RED));
    }
}