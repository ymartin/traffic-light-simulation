package ymartin.traffic.intersection;

import java.util.ArrayList;
import java.util.List;

public class MockIntersectionListener implements Intersection.IntersectionListener {
    private List<IntersectionSnapshot> intersectionSnapshotList;

    public MockIntersectionListener() {
        this.intersectionSnapshotList = new ArrayList<>();
    }

    public IntersectionSnapshot getLastIntersectionState() {
        if (intersectionSnapshotList.isEmpty()) {
            return null;
        }
        return intersectionSnapshotList.get(intersectionSnapshotList.size() - 1);
    }

    @Override
    public void notifyChange(IntersectionSnapshot intersectionSnapshot) {
        intersectionSnapshotList.add(intersectionSnapshot);
    }
}
