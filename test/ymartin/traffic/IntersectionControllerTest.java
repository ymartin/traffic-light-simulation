package ymartin.traffic;


import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class IntersectionControllerTest {

    private IntersectionController intersectionController;
    private SystemTime systemTime;

    @Before
    public void setUp() throws Exception {
        systemTime = new FrozenSystemTime(LocalDateTime.now());
        intersectionController = new IntersectionController(systemTime, Duration.ofMinutes(4).plusSeconds(30));
    }

    @Test
    public void shouldReturnFirstStateWithNorthSouthAsGreenAndEastWestAsRed() {
        IntersectionState state = intersectionController.simulate().get(0);

        assertThat(state.getDateTime(), is(systemTime.now()));
        assertThat(state.getIntersection().getNorth().getColor(), is(TrafficLight.Color.GREEN));
        assertThat(state.getIntersection().getSouth().getColor(), is(TrafficLight.Color.GREEN));
        assertThat(state.getIntersection().getEast().getColor(), is(TrafficLight.Color.RED));
        assertThat(state.getIntersection().getWest().getColor(), is(TrafficLight.Color.RED));
    }

    @Test
    public void shouldReturnSecondStateWithNorthSouthAsYellowAndEastWestAsRed() {
        IntersectionState state = intersectionController.simulate().get(1);

        assertThat(state.getDateTime(), is(systemTime.now().plus(Duration.ofMinutes(4).plusSeconds(30))));
        assertThat(state.getIntersection().getNorth().getColor(), is(TrafficLight.Color.YELLOW));
        assertThat(state.getIntersection().getSouth().getColor(), is(TrafficLight.Color.YELLOW));
        assertThat(state.getIntersection().getEast().getColor(), is(TrafficLight.Color.RED));
        assertThat(state.getIntersection().getWest().getColor(), is(TrafficLight.Color.RED));
    }
}