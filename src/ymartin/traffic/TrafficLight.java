package ymartin.traffic;

public class TrafficLight {
    private final Color color;

    public TrafficLight(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public enum Color {
        GREEN, RED
    }
}
