import java.awt.*;

public class Node {
    private boolean value;
    private Point location;
    private boolean input = false;

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

    public boolean hasInput() { return input; }

    public void setInput(boolean hasInput) {
        input = hasInput;
    }
}
