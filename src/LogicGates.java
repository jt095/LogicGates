import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Function;

public class LogicGates extends JPanel {
    // keyboard inputs
    private String lastKeyPressed = "";
    private Point pointStart = null;
    private Point pointEnd   = null;

    private final int nodeSize = 20;
    private static final int FPS = 60;

    private Node startNode;
    private Node endNode;
    private ArrayList<Wire> wires = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();

    public LogicGates() {
        // Add a KeyListener to capture keyboard input
        this.setFocusable(true);  // Make sure the panel can receive focus
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Store the last key pressed (e.g., "1", "2", "A", etc.)
                lastKeyPressed = String.valueOf(e.getKeyChar());
                System.out.println(lastKeyPressed);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pointStart = e.getPoint();
                if (lastKeyPressed.equals("w")) {
                    startNode = checkForNode(pointStart);
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (lastKeyPressed.equals("n")) {
                    Node n = new Node(pointStart);
                    nodes.add(n);
                    System.out.printf("added new node at location: %s", n.getLocation());
                } if (lastKeyPressed.equals("w")) {
                    if (startNode != null) {
                        endNode = checkForNode(pointEnd);
                        if (endNode != null) {
                            Node[] outputs = new Node[]{endNode};
                            Wire w = new Wire(startNode, outputs, startNode.getLocation(), endNode.getLocation());
                            wires.add(w);

                        }
                    }
                }
                if (pointStart != null ) {
                    pointStart = null;
                }
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseMoved(MouseEvent e) {
                pointEnd = e.getPoint();
            }

            public void mouseDragged(MouseEvent e) {
                pointEnd = e.getPoint();
                repaint();
            }
        });
    }

    public void paint(Graphics g) {
        super.paint(g);
        for(Node n : nodes) {
            drawNode(g, n.getLocation());
        }
        for (Wire w : wires) {
            drawWire(g, w.start, w.end);
        }
        if (lastKeyPressed.equals("w")) {
            drawWire(g, pointStart, pointEnd);
        }
    }

    private void drawNode(Graphics g, Point p) {
        g.setColor(Color.BLUE);
        g.drawOval(p.x - nodeSize/2, p.y - nodeSize/2, nodeSize, nodeSize);
    }

    private void drawWire(Graphics g, Point start, Point end) {
        if (start == null || end == null) return;
        g.setColor(Color.RED);
        Point[] points = createWirePoints(start, end);
        if (points.length == 2) {
            g.drawLine(start.x, start.y, end.x, end.y);
        } else {
            g.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
            g.drawLine(points[1].x, points[1].y, points[2].x, points[2].y);
            g.drawLine(points[2].x, points[2].y, points[3].x, points[3].y);
        }

    }

    private Node checkForNode(Point p) {
        for (Node n : nodes) {
            if (withinCircle(p, n.getLocation())) {
                return n;
            }
        }
        return null;
    }

    private boolean withinCircle(Point p, Point circle) {
        return (p.x < circle.x + nodeSize / 2 &&
            p.x > circle.x - nodeSize / 2 &&
            p.y < circle.y + nodeSize / 2 &&
            p.y > circle.y - nodeSize / 2);
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Draw a Red Line");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 300);
        f.setLocation(300, 300);
        f.setResizable(false);
        LogicGates panel = new LogicGates();
        f.add(panel);
        f.setVisible(true);



        panel.repaint();       // Repaint the panel with the new state


//        // create input board
//        Node inputA = new Node();
//        Node inputB = new Node();
//
//
//        Function<boolean[], Boolean> nandLogic = (inputs) -> (!(inputs[0] && inputs[1]));
//        GenericChip nand = new GenericChip(2, 1, "NAND", nandLogic);
//
//        Wire aToNandA = new Wire(inputA, new Node[]{nand.getNodeAtIndex(0)});
//        Wire bToNandB = new Wire(inputB, new Node[]{nand.getNodeAtIndex(1)});
//        inputA.setValue(true);
//        inputB.setValue(true);
//
//        aToNandA.update();
//        bToNandB.update();
//
//        System.out.printf("NAND Output for inputs: %s, %s = %s", nand.getInputAtIndex(0), nand.getInputAtIndex(1), nand.getOutput());

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
