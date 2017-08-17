package ymartin.traffic.util;

import ymartin.traffic.util.SystemTime;

import java.time.LocalDateTime;

public class StubSystemTime implements SystemTime {

    private LocalDateTime currentDateTime;

    public StubSystemTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    @Override
    public LocalDateTime now() {
        return currentDateTime;
    }
}
