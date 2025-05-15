import java.awt.*;

public class Node {
    private boolean value;
    private Point location;

    public Node(Point location) {
        this.location = location;
    }

    public Point getLocation() {
        return location;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }
}
