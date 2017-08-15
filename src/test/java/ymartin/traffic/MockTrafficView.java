package ymartin.traffic;

import java.util.ArrayList;
import java.util.List;

public class MockTrafficView implements TrafficController.TrafficView {
    private List<IntersectionState> intersectionStateList;

    public MockTrafficView() {
        this.intersectionStateList = new ArrayList<>();
    }

    public IntersectionState getLastIntersectionState() {
        if (intersectionStateList.isEmpty()) {
            return null;
        }
        return intersectionStateList.get(intersectionStateList.size() - 1);
    }

    @Override
    public void intersectionChange(IntersectionState intersectionState) {
        intersectionStateList.add(intersectionState);
    }
}
