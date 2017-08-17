package ymartin.traffic;

import java.util.ArrayList;
import java.util.List;

public class MockIntersectionView implements Intersection.IntersectionView {
    private List<IntersectionSnapshot> intersectionSnapshotList;

    public MockIntersectionView() {
        this.intersectionSnapshotList = new ArrayList<>();
    }

    public IntersectionSnapshot getLastIntersectionState() {
        if (intersectionSnapshotList.isEmpty()) {
            return null;
        }
        return intersectionSnapshotList.get(intersectionSnapshotList.size() - 1);
    }

    @Override
    public void render(IntersectionSnapshot intersectionSnapshot) {
        intersectionSnapshotList.add(intersectionSnapshot);
    }
}
