package pipeline.djurdjevic.elena.processing;

import pipeline.djurdjevic.elena.instructions.*;

import java.util.LinkedList;

public class Pipeline {

    private LinkedList<Instruction> instructions;
    private LinkedList<Instruction> fetched = new LinkedList<>();
    private LinkedList<Instruction> decoded = new LinkedList<>();
    private LinkedList<Instruction> executed = new LinkedList<>();
    private LinkedList<Instruction> accessed = new LinkedList<>();
    private LinkedList<Instruction> finished = new LinkedList<>();
    private final int numberOfInstructions;
    private final boolean operandForwarding;
    private int currentCycle = 1;

    public Pipeline(LinkedList<Instruction> instructions, boolean operandForwarding) {
        this.instructions = instructions;
        numberOfInstructions = instructions.size();
        this.operandForwarding = operandForwarding;
    }

    public void writeBack() {
        Instruction instruction = null;
        if (!accessed.isEmpty()) {
            instruction = accessed.pollFirst();
            System.out.println(instruction + " write back");
            finished.addLast(instruction);
        }
        memoryAccess();
        if(!operandForwarding && instruction != null){
            instruction.setRegistersBusy(false);
            Instruction forwardedInstruction = fetched.peekFirst();
            if(forwardedInstruction != null){
                System.out.println(instruction + " -> writeBack forwards operand to -> " + forwardedInstruction + " decode");
            }
        }
    }

    public void memoryAccess() {

        if (!executed.isEmpty()) {
            Instruction instruction = executed.peekFirst();
            System.out.println(instruction + " memory access");
            if (instruction.getMemCurrentCycle() < instruction.getMemCycles()) {
                instruction.updateMemCurrentCycle();
            } else {
                accessed.addLast(executed.pollFirst());
            }
        }
        execute();
    }

    public void execute() {

        Instruction instruction = null;
        if (!decoded.isEmpty()) {
            instruction = decoded.peekFirst();
            System.out.println(instruction + " execute");
            if (instruction.getExCurrentCycle() < instruction.getExCycles()) {
                instruction.updateExCurrentCycle();
            } else {
                executed.addLast(decoded.pollFirst());
                instruction.resetExCurrentCycle();
            }
        }
        decode();
        if(operandForwarding && instruction != null && instruction.getExCurrentCycle() == 0){
            instruction.setRegistersBusy(false);
            Instruction forwardedInstruction = fetched.peekFirst();
            if(forwardedInstruction != null){
                System.out.println(instruction + " -> execute forwards operand to -> " + forwardedInstruction + " decode");
            }
        }
    }

    public void decode() {

        if (!fetched.isEmpty()) {
            Instruction instruction = fetched.pollFirst();
            if (instruction.isDecodable()) {
                System.out.println(instruction + " decode");
                instruction.setRegistersBusy(true);
                decoded.addLast(instruction);
            } else {
                Instruction instruction2 = executed.peekFirst();
                if (instruction2 != null) {
                    System.out.print("Checking for hazards... -> ");
                    detectHazard(instruction, instruction2);
                }
                System.out.println(instruction + " is waiting..");
                fetched.addFirst(instruction);
            }
        }
        fetch();
    }

    public void fetch() {

        if (!instructions.isEmpty()) {
            Instruction instruction = instructions.pollFirst();
            System.out.println(instruction + " fetch");
            fetched.addLast(instruction);
        }
    }

    public void detectHazard(Instruction instruction1, Instruction instruction2) {

        if (instruction1 instanceof UnaryInstruction unaryInstruction) {
            if (unaryInstruction.getSource().equals(instruction2.getDestination())) {
                System.out.println("Read After Write Hazard detected");
            }
            else if(unaryInstruction.getDestination().equals(instruction2.getDestination())){
                System.out.println("Write After Write hazard detected");
            }
            else if(instruction2 instanceof UnaryInstruction unaryInstruction2 && unaryInstruction2.getSource().equals(unaryInstruction.getDestination())){
                System.out.println("Write After Read hazard detected");
            }
            else if (instruction2 instanceof BinaryInstruction binaryInstruction2 && (binaryInstruction2.getSource1().equals(unaryInstruction.getDestination()) || binaryInstruction2.getSource2().equals(unaryInstruction.getDestination()))) {
                System.out.println("Write After Read hazard detected");
            } else {
                System.out.println("None hazard detected");
            }
        } else {
            BinaryInstruction binaryInstruction = (BinaryInstruction) instruction1;
            if (binaryInstruction.getSource1().equals(instruction2.getDestination()) || binaryInstruction.getSource2().equals(instruction2.getDestination())) {
                System.out.println("Read After Write Hazard detected");
            }
            else if(binaryInstruction.getDestination().equals(instruction2.getDestination())){
                System.out.println("Write After Write hazard detected");
            }
            else if(instruction2 instanceof UnaryInstruction unaryInstruction2 && unaryInstruction2.getSource().equals(binaryInstruction.getDestination())){
                System.out.println("Write After Read hazard detected");
            }
            else if (instruction2 instanceof BinaryInstruction binaryInstruction2 && (binaryInstruction2.getSource1().equals(binaryInstruction.getDestination()) || binaryInstruction2.getSource2().equals(binaryInstruction.getDestination()))) {
                System.out.println("Write After Read hazard detected");
            }
            else {
                System.out.println("None hazard detected");
            }
        }
    }

    public void start() {

        System.out.println("Ciklus " + currentCycle);
        currentCycle++;
        fetch();
        System.out.println("Ciklus " + currentCycle);
        currentCycle++;
        decode();
        System.out.println("Ciklus " + currentCycle);
        currentCycle++;
        execute();
        System.out.println("Ciklus " + currentCycle);
        currentCycle++;
        memoryAccess();
        System.out.println("Ciklus " + currentCycle);
        currentCycle++;
        writeBack();
        while(finished.size() != numberOfInstructions){
            System.out.println("Ciklus " + currentCycle);
            currentCycle++;
            writeBack();
        }
    }

    public static void main(String[] args){
        Register rax = new Register("RAX");
        Register rbx = new Register("RBX");
        Register rcx = new Register("RCX");
        Register rbi = new Register("RBI");
        Register rci = new Register("RCI");
        Register rai = new Register("RAI");

        LinkedList<Instruction> instructions = new LinkedList<>();
        instructions.addLast(new SubInstruction(rax, rbx, rcx));
        instructions.addLast(new MulInstruction(rcx, rci, rai));
        instructions.addLast(new LoadInstruction(rax, rci));
        instructions.addLast(new MulInstruction(rax, rci, rai));
        instructions.addLast(new LoadInstruction(rax, rci));

        Pipeline pipeline = new Pipeline(instructions, false);
        pipeline.start();
    }
}
