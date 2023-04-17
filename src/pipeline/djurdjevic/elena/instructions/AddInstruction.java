package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class AddInstruction extends BinaryInstruction{
    public AddInstruction(Register source1, Register source2, Register destination) {
        super("ADD", 1, 1, source1, source2, destination);
    }
}
