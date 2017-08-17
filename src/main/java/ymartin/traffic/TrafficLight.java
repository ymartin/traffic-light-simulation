package ymartin.traffic;

public class TrafficLight {
    private Colour colour;

    public TrafficLight(Colour colour) {
        this.colour = colour;
    }

    public Colour getColour() {
        return colour;
    }

    public enum Colour {
        GREEN, RED, YELLOW
    }
}
