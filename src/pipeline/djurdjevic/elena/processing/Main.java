package pipeline.djurdjevic.elena.processing;

import pipeline.djurdjevic.elena.instructions.*;

import java.util.LinkedList;

public class Main {
    public static LinkedList<Instruction> initializeData() {
        Register rbi = new Register("RBI");
        Register rbx = new Register("RBX");
        Register rcx = new Register("RCX");
        Register rci = new Register("RCI");
        Register rai = new Register("RAI");
        Register rax = new Register("RAX");

        LinkedList<Instruction> instructions = new LinkedList<>();
        instructions.addLast(new AddInstruction(rax, rbx, rcx));
        instructions.addLast(new MulInstruction(rcx, rci, rai));
        instructions.addLast(new LoadInstruction(rax, rci));
        instructions.addLast(new DivInstruction(rax, rci, rai));
        instructions.addLast(new SubInstruction(rcx, rbx, rbi));
        instructions.addLast(new StoreInstruction(rax, rci));

        return instructions;
    }

    public static void main(String[] args) {
        Pipeline pipeline = new Pipeline(initializeData(), false);
        pipeline.start();
    }
}
