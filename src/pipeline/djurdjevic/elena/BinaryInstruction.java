package pipeline.djurdjevic.elena;

public class BinaryInstruction extends Instruction{

    private Register source1;
    private Register source2;
    private Register destination;
    public BinaryInstruction(String name, int cycles, Register source1, Register source2, Register destination) {
        super(name, cycles);
        this.source1 = source1;
        this.source2 = source2;
        this.destination = destination;
    }
}
