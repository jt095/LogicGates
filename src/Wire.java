import java.awt.*;

public class Wire {
    private Node input;
    private Node[] outputs;
    private boolean value;
    Point start;
    Point end;

    public Wire(Node input, Node[] outputs, Point start, Point end) {
        this.input = input;
        this.outputs = outputs;
        this.start = start;
        this.end = end;
    }

    public boolean getValue() {
        return this.input.getValue();
    }

    public void update() {
        boolean val = getValue();
        for (Node output : outputs) {
            output.setValue(val);
        }
    }
}
