import java.awt.*;
import java.util.Random;

public class Wire {
    private Node input;
    private Node[] outputs;
    private boolean value;
    Point start;
    Point end;
    private Color activeColor = Color.RED;
    private Color inactiveColor = Color.LIGHT_GRAY;

    public Wire(Node input, Node[] outputs, Point start, Point end) {
        this.input = input;
        this.outputs = outputs;
        this.start = start;
        this.end = end;
    }

    public boolean getValue() {
        return this.input.getValue();
    }

    public Color getColor() {
        return getValue() ? activeColor : inactiveColor;
    }

    public void update() {
        boolean val = getValue();
        for (Node output : outputs) {
            output.setValue(val);
        }
    }

    public static void drawWire(Graphics g, Point start, Point end, Color c) {
        if (start == null || end == null) return;
        g.setColor(c);
        Point[] points = createWirePoints(start, end);
        if (points.length == 2) {
            g.drawLine(start.x, start.y, end.x, end.y);
        } else {
            g.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
            g.drawLine(points[1].x, points[1].y, points[2].x, points[2].y);
            g.drawLine(points[2].x, points[2].y, points[3].x, points[3].y);
        }

    }

    public static Point[] createWirePoints(Point start, Point end) {
        // create three lines to keep wire straightPoint[] points;
        if (start.y == end.y) {
            return new Point[] {start, end};
        }

        Point[] points = new Point[4];
        points[0] = start;
        points[3] = end;
        points[1] = new Point((start.x + end.x)/2, start.y);
        points[2] = new Point((start.x + end.x)/2, end.y);
        return points;
    }
}
