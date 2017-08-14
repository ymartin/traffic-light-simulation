package ymartin.traffic;

public class Intersection {

    private final TrafficLight north;
    private final TrafficLight south;
    private final TrafficLight east;
    private final TrafficLight west;

    public Intersection(TrafficLight north, TrafficLight south, TrafficLight east, TrafficLight west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public TrafficLight getNorth() {
        return north;
    }

    public TrafficLight getSouth() {
        return south;
    }

    public TrafficLight getEast() {
        return east;
    }

    public TrafficLight getWest() {
        return west;
    }
}
