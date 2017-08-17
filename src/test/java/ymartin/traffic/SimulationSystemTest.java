package ymartin.traffic;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Test;

import java.io.*;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class SimulationSystemTest {

    @Test
    public void shouldReturnValidIntersectionOrder() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Simulation simulation = new Simulation(new PrintStream(out), Duration.ofSeconds(1), Duration.ofMillis(30));

        simulation.run(Duration.ofSeconds(3));
        Thread.sleep(Duration.ofSeconds(4).toMillis());

        BufferedReader reader = new BufferedReader(new StringReader(out.toString()));
        assertThat(reader.readLine(), containsString("Date", "Time", "North", "South", "East", "West"));
        assertThat(reader.readLine(), containsString("GREEN", "GREEN", "RED", "RED"));
        assertThat(reader.readLine(), containsString("YELLOW", "YELLOW", "RED", "RED"));
        assertThat(reader.readLine(), containsString("RED", "RED", "GREEN", "GREEN"));
        assertThat(reader.readLine(), containsString("RED", "RED", "YELLOW", "YELLOW"));
        assertThat(reader.readLine(), containsString("GREEN", "GREEN", "RED", "RED"));
        assertThat(reader.readLine(), is(nullValue()));
    }

    private Matcher<String> containsString(String... substrings) {
        return allOf(Arrays.stream(substrings).map(CoreMatchers::containsString).collect(Collectors.toList()));
    }
}