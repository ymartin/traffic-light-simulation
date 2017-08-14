package ymartin.traffic;

import java.time.LocalDateTime;

public class FrozenSystemTime implements SystemTime {

    private final LocalDateTime dateTime;

    public FrozenSystemTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public LocalDateTime now() {
        return dateTime;
    }
}
