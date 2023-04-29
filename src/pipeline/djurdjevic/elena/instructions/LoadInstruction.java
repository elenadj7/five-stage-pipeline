package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class LoadInstruction extends UnaryInstruction {
    public LoadInstruction(Register source, Register destination) {
        super("LOAD", 0, 2, source, destination);
    }
}
