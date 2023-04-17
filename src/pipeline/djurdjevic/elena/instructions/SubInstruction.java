package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class SubInstruction extends BinaryInstruction{
    public SubInstruction(Register source1, Register source2, Register destination) {
        super("SUB", 1, 1, source1, source2, destination);
    }
}
