package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public abstract class UnaryInstruction extends Instruction {

    private Register source;
    private Register destination;

    public UnaryInstruction(String name, int exCycles, int memCycles, Register source, Register destination) {
        super(name, exCycles, memCycles);
        this.source = source;
        this.destination = destination;
    }
}
