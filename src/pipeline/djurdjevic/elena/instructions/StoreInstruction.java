package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

public class StoreInstruction extends UnaryInstruction {
    public StoreInstruction(Register source, Register destination) {
        super("STORE", 0, 2, source, destination);
    }
}
