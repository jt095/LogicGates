import java.awt.*;
import java.util.function.Function;

public class GenericChip {
    private int numInputs;
    private int numOutputs;
    private final String chipName;
    private final ChipNode[] inputs;
    private final ChipNode output;
    private Function<boolean[], Boolean> logicFunction;
    private Point position;
    private Point[] cornerPositions;
    private Point[] inputPositions;
    private Point outputPosition;
    private final int WIDTH = 150;
    private final int HEIGHT = 75;
    private final int NODESIZE = 15;
    private Color chipColor = Color.GREEN;
    private Color nodeColor = Color.LIGHT_GRAY;
    private final ChipType CHIPTYPE;

    public GenericChip(String chipName, Point location, ChipType chipType) {
        this.chipName = chipName;
        this.position = location;
        this.CHIPTYPE = chipType;
        // create coordinates for all components
        createChip();
        this.inputs = new ChipNode[numInputs];
        createCorners();
        createInputPositions();
        createOutputPositions();
        this.output = new ChipNode(this, "output", 0, outputPosition);
        generateNodes();
    }

    public void reposition(Point p) {
        position = p;
        // recalculate component positions
        updatePositions();
    }

    public ChipNode[] getChipNodes() {
        ChipNode[] nodes = new ChipNode[numInputs + numOutputs];
        for (int i = 0; i < numInputs; i++) {
            nodes[i] = inputs[i];
        }
        nodes[numInputs + numOutputs - 1] = output;
        return nodes;
    }
    public Point[] getCornerPositions() { return cornerPositions; }

    public Point[] getInputPositions() { return inputPositions; }

    public Point getOutputPosition() { return outputPosition; }

    public void performLogic() {
        setOutput(logicFunction.apply(getInputValues()));
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public int getWIDTH() { return WIDTH; }

    public int getHEIGHT() { return HEIGHT; }

    public int getNODESIZE() { return NODESIZE; }

    public String getChipName() {
        return chipName;
    }

    public boolean getInputAtIndex(int index) {
        if (index < 0 || index > inputs.length) {
            System.out.printf("Input index %d out of range for inputs of size %d", index, numInputs);
            throw new IndexOutOfBoundsException();
        }
        return inputs[index].getValue();
    }

    public Node getNodeAtIndex(int index) {
        if (index < 0 || index > inputs.length) {
            System.out.printf("Input index %d out of range for inputs of size %d", index, numInputs);
            throw new IndexOutOfBoundsException();
        }
        return inputs[index];
    }

    public boolean getOutput() {
        return output.getValue();
    }

    public void setInputAtIndex(int index, boolean val) {
        if (index < 0 || index > inputs.length) {
            System.out.printf("Input index %d out of range for inputs of size %d", index, numInputs);
            throw new IndexOutOfBoundsException();
        }
        inputs[index].setValue(val);
    }

    public void setOutput(boolean val) {
        output.setValue(val);
    }

    public void drawChip(Graphics g) {
        Point topLeft = getCornerPositions()[0];
        g.setColor(chipColor);
        g.drawRect(topLeft.x, topLeft.y, getWIDTH(), getHEIGHT());
        g.setColor(nodeColor);
        for (Point p : getInputPositions()) {
            g.drawOval(p.x - getNODESIZE()/2, p.y - getNODESIZE()/2,getNODESIZE(), getNODESIZE());
        }

        g.drawOval(getOutputPosition().x - getNODESIZE()/2, getOutputPosition().y - getNODESIZE()/2, getNODESIZE(), getNODESIZE());
    }

    private void generateNodes() {
        for (int i = 0; i < numInputs; i++) {
            ChipNode node = new ChipNode(this, "input", i, inputPositions[i]);
            inputs[i] = node;
        }
    }

    private boolean[] getInputValues() {
        boolean[] vals = new boolean[numInputs];
        for (int i = 0; i < numInputs; i++) {
            vals[i] = inputs[i].getValue();
        }
        return vals;
    }

    private void createChip() {
        switch (CHIPTYPE) {
            case NAND ->
                    createNand();
            default ->
                    createNand();
        }
    }

    private void createNand() {
        this.numInputs = 2;
        this.numOutputs = 1;
        this.logicFunction = (inputs1) -> (!(inputs1[0] && inputs1[1]));
    }

    private void createCorners() {
        Point topLeft = new Point(position.x - WIDTH/2, position.y - HEIGHT/2);
        Point botLeft = new Point(position.x - WIDTH/2, position.y + HEIGHT/2);
        Point botRight = new Point(position.x + WIDTH/2, position.y + HEIGHT/2);
        Point topRight = new Point(position.x + WIDTH/2, position.y - HEIGHT/2);
        cornerPositions = new Point[]{topLeft, botLeft, botRight, topRight};
    }

    private void createInputPositions() {
        int numInputs = getNumInputs();
        Point[] inputPos = new Point[numInputs];
        // height / num inputs (ex 20/ 2 = 10
        int divider = HEIGHT / numInputs;
        for (int i = 0; i < numInputs; i++) {
            int posy = Math.round(((float) (1 + i * 2) /2)*divider);
            Point pos = new Point(cornerPositions[0].x, cornerPositions[0].y + posy);
            inputPos[i] = pos;
        }
        inputPositions = inputPos;
    }

    private void createOutputPositions() {
        int divider = HEIGHT / 2;
        outputPosition = new Point(cornerPositions[3].x, cornerPositions[3].y + divider);
    }

    private void updatePositions() {
        createCorners();
        createInputPositions();
        createOutputPositions();
    }
}
