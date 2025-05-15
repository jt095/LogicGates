import java.awt.*;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class GenericChip {
    private final int numInputs;
    private final int numOutputs;
    private final String chipName;
    private final Node[] inputs;
    private final Node output;
    private final Function<boolean[], Boolean> logicFunction;
    private Point location;

    public GenericChip(int numInputs, int numOutputs, String chipName, Function<boolean[], Boolean> logicFunction, Point location) {
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.chipName = chipName;
        this.logicFunction = logicFunction;
        this.inputs = new Node[numInputs];
        this.location = location;
        output = new ChipNode(this, "output", 0, location);
        generateNodes();
    }

    private void generateNodes() {
        for (int i = 0; i < numInputs; i++) {
            Node node = new ChipNode(this, "input", i, location);
            inputs[i] = node;
        }

    }

    public void performLogic() {
        setOutput(logicFunction.apply(getInputValues()));
    }

    public int getNumInputs() {
        return numInputs;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

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

    private boolean[] getInputValues() {
        boolean[] vals = new boolean[numInputs];
        for (int i = 0; i < numInputs; i++) {
            vals[i] = inputs[i].getValue();
        }
        return vals;
    }
}
