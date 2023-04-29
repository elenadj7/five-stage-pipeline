package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public abstract class BinaryInstruction extends Instruction {

    protected Register source1;
    protected Register source2;

    public BinaryInstruction(String name, int exCycles, int memCycles, Register source1, Register source2, Register destination) {
        super(name, exCycles, memCycles, destination);
        this.source1 = source1;
        this.source2 = source2;
        registers.add(source1);
        registers.add(source2);
    }

    public Register getSource1() {
        return source1;
    }

    public Register getSource2() {
        return source2;
    }
}
