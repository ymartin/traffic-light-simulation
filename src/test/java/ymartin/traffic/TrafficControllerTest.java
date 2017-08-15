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
                fakeTrafficUserInterface, fakeExecutorService,
                systemTime, Duration.ofMinutes(4).plusSeconds(30)
        );
    }

    @Test
    public void shouldReturnNorthSouthAsGreenAndEastWestAsRedInitially() throws InterruptedException {
        trafficController.simulate();

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getDateTime(), is(systemTime.now()));
        assertThat(state.getIntersection().getNorthLight().getColor(), is(TrafficLight.Color.GREEN));
        assertThat(state.getIntersection().getSouthLight().getColor(), is(TrafficLight.Color.GREEN));
        assertThat(state.getIntersection().getEastLight().getColor(), is(TrafficLight.Color.RED));
        assertThat(state.getIntersection().getWestLight().getColor(), is(TrafficLight.Color.RED));
    }

    @Test
    public void shouldReturnNorthSouthAsYellowAndEastWestAsRedAfter4Min30Sec() {
        trafficController.simulate();
        fakeExecutorService.executeScheduled(Duration.ofMinutes(4).plusSeconds(30));

        IntersectionState state = fakeTrafficUserInterface.getLastIntersectionState();
        assertThat(state.getDateTime(), is(systemTime.now().plus(Duration.ofMinutes(4).plusSeconds(30))));
        assertThat(state.getIntersection().getNorthLight().getColor(), is(TrafficLight.Color.YELLOW));
        assertThat(state.getIntersection().getSouthLight().getColor(), is(TrafficLight.Color.YELLOW));
        assertThat(state.getIntersection().getEastLight().getColor(), is(TrafficLight.Color.RED));
        assertThat(state.getIntersection().getWestLight().getColor(), is(TrafficLight.Color.RED));
    }

}