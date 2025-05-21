import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

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
    private ArrayList<Node> individualNodes = new ArrayList<>();
    private ArrayList<GenericChip> genericChips = new ArrayList<>();

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
                if (lastKeyPressed.equals("c")) {
                    GenericChip c = new GenericChip("NAND", pointStart, ChipType.NAND);
                    genericChips.add(c);
                }
                // first click on wire is the output node
                if (lastKeyPressed.equals("w")) {
                    startNode = getOutputNode(pointStart);
                }
                repaint();
            }

            public void mouseReleased(MouseEvent e) {
                if (lastKeyPressed.equals("n")) {
                    Node n = new Node(pointStart);
                    individualNodes.add(n);
                    System.out.printf("added new node at location: %s", n.getLocation());
                } if (lastKeyPressed.equals("w")) {
                    if (startNode != null) {
                        // mouse release is input node
                        endNode = getInputNode(pointEnd);
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
        setBackground(Color.DARK_GRAY);

        for(Node n : individualNodes) {
            drawIndividualNode(g, n.getLocation());
        }
        for (Wire w : wires) {
            Wire.drawWire(g, w.start, w.end, w.getColor());
        }
        for (GenericChip c : genericChips) {
            c.drawChip(g);
        }
        if (lastKeyPressed.equals("w")) {
            Wire.drawWire(g, pointStart, pointEnd, Color.LIGHT_GRAY);
        }
    }

    private void drawIndividualNode(Graphics g, Point p) {
        g.setColor(Color.BLUE);
        g.drawOval(p.x - nodeSize/2, p.y - nodeSize/2, nodeSize, nodeSize);
    }


    private Node getInputNode(Point p) {
        for (Node n : individualNodes) {
            if (withinCircle(p, n.getLocation())) {
                if (!n.hasInput()) {
                    n.setInput(true);
                    return n;
                }
                System.out.println("This node already has an input.");
                return null;
            }
        }

        for (GenericChip c : genericChips) {
            for (ChipNode n : c.getChipNodes()) {
                if (withinCircle(p, n.getLocation())) {
                    if (n.getSide().equals("input")) {
                        if (!n.hasInput()) {
                            n.setInput(true);
                            return n;
                        }
                        System.out.println("This node already has an input.");
                        return null;
                    }
                    System.out.println("This is an output only node.");
                    return null;
                }
            }
        }

        return null;
    }

    private Node getOutputNode(Point p) {
        for (Node n : individualNodes) {
            if (withinCircle(p, n.getLocation())) {
                return n;
            }
        }

        for (GenericChip c : genericChips) {
            for (ChipNode n : c.getChipNodes()) {
                if (withinCircle(p, n.getLocation())) {
                    return n;
                }
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
        JFrame f = new JFrame("Create Circuits");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 1000);
        f.setLocation(10, 10);
        f.setResizable(false);
        LogicGates panel = new LogicGates();
        f.add(panel);
        f.getContentPane().setBackground(Color.BLACK);
        f.setVisible(true);


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


}
