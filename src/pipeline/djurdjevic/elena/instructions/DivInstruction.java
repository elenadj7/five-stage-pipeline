package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class DivInstruction extends BinaryInstruction {
    public DivInstruction(Register source1, Register source2, Register destination) {
        super("DIV", 1, 0, source1, source2, destination);
    }
}
