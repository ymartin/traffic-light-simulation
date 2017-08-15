package ymartin.traffic;

public class Intersection {

    private final TrafficLight northLight;
    private final TrafficLight southLight;
    private final TrafficLight eastLight;
    private final TrafficLight westLight;

    public Intersection(TrafficLight northLight, TrafficLight southLight, TrafficLight eastLight, TrafficLight westLight) {
        this.northLight = northLight;
        this.southLight = southLight;
        this.eastLight = eastLight;
        this.westLight = westLight;
    }

    public TrafficLight getNorthLight() {
        return northLight;
    }

    public TrafficLight getSouthLight() {
        return southLight;
    }

    public TrafficLight getEastLight() {
        return eastLight;
    }

    public TrafficLight getWestLight() {
        return westLight;
    }
}
