package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public abstract class BinaryInstruction extends Instruction {

    private Register source1;
    private Register source2;
    private Register destination;
    public BinaryInstruction(String name, int exCycles, int memCycles, Register source1, Register source2, Register destination) {
        super(name, exCycles, memCycles);
        this.source1 = source1;
        this.source2 = source2;
        this.destination = destination;
    }
}
