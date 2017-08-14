package ymartin.traffic;

public class Intersection {

    public Intersection() {
    }

    public TrafficLight getNorth() {
        return new TrafficLight(TrafficLight.Color.GREEN);
    }

    public TrafficLight getSouth() {
        return new TrafficLight(TrafficLight.Color.GREEN);
    }

    public TrafficLight getEast() {
        return new TrafficLight(TrafficLight.Color.RED);
    }

    public TrafficLight getWest() {
        return new TrafficLight(TrafficLight.Color.RED);
    }
}
