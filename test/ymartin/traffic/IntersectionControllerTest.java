package ymartin.traffic;


import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class IntersectionControllerTest {

    private IntersectionController intersectionController;
    private SystemTime systemTime;

    @Before
    public void setUp() throws Exception {
        systemTime = new FrozenSystemTime(LocalDateTime.now());
        intersectionController = new IntersectionController(systemTime);
    }

    @Test
    public void shouldReturnFirstStateWithCurrentDate() {
        IntersectionState state = intersectionController.simulate();

        assertThat(state.getDateTime(), is(systemTime.now()));
    }

    @Test
    public void shouldReturnFirstStateWithNorthSouthLanesAsGreen() {
        Intersection intersection = intersectionController.simulate().getIntersection();

        assertThat(intersection.getNorth().getColor(), is(TrafficLight.Color.GREEN));
        assertThat(intersection.getSouth().getColor(), is(TrafficLight.Color.GREEN));
    }

    @Test
    public void shouldReturnFirstStateWithEastWestLanesAsRed() {
        Intersection intersection = intersectionController.simulate().getIntersection();

        assertThat(intersection.getEast().getColor(), is(TrafficLight.Color.RED));
        assertThat(intersection.getWest().getColor(), is(TrafficLight.Color.RED));
    }
}