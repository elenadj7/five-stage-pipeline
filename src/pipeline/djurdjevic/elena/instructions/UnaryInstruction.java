package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public abstract class UnaryInstruction extends Instruction {

    protected Register source;

    public UnaryInstruction(String name, int exCycles, int memCycles, Register source, Register destination) {
        super(name, exCycles, memCycles, destination);
        this.source = source;
        registers.add(source);
    }

    public Register getSource() {
        return source;
    }

}
