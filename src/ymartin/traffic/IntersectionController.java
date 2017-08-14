package ymartin.traffic;

public class IntersectionController {

    private SystemTime systemTime;

    public IntersectionController(SystemTime systemTime) {
        this.systemTime = systemTime;
    }

    public IntersectionState simulate() {
        return new IntersectionState(new Intersection(), systemTime.now());
    }

}
