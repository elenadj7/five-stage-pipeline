package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class LoadInstruction extends UnaryInstruction{
    public LoadInstruction(String name, int cycles, Register source, Register destination) {
        super("LOAD", 1, 3, source, destination);
    }
}
