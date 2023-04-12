package pipeline.djurdjevic.elena;

public class UnaryInstruction extends Instruction{

    private Register source;
    private Register destination;

    public UnaryInstruction(String name, int cycles, Register source, Register destination) {
        super(name, cycles);
        this.source = source;
        this.destination = destination;
    }
}
