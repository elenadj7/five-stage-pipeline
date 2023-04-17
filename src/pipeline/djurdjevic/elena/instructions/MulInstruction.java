package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class MulInstruction extends BinaryInstruction{
    public MulInstruction(Register source1, Register source2, Register destination) {
        super("MUL", 2, 1, source1, source2, destination);
    }
}
