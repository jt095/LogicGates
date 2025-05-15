import java.awt.*;

public class ChipNode extends Node{
    private final GenericChip chip;
    private final String side;
    private final int ioNumber;

    public ChipNode(GenericChip chip, String side, int ioNumber, Point location) {
        super(location);
        this.chip = chip;
        this.side = side;
        this.ioNumber = ioNumber;
    }

    public GenericChip getChip() {
        return chip;
    }

    public String getSide() {
        return side;
    }

    public int getIoNumber() {
        return ioNumber;
    }
}
