package pipeline.djurdjevic.elena.processing;

import pipeline.djurdjevic.elena.instructions.*;

import java.util.ArrayList;
import java.util.LinkedList;

public class Pipeline {

    private LinkedList<Instruction> instructions;
    private LinkedList<Instruction> fetched = new LinkedList<>();
    private LinkedList<Instruction> decoded = new LinkedList<>();
    private LinkedList<Instruction> executed = new LinkedList<>();
    private LinkedList<Instruction> accessed = new LinkedList<>();
    private LinkedList<Instruction> finished = new LinkedList<>();
    private ArrayList<ArrayList<String>> printMatrix = new ArrayList<>();
    private final int numberOfInstructions;
    private final boolean operandForwarding;
    private int currentCycle = 1;

    public Pipeline(LinkedList<Instruction> instructions, boolean operandForwarding) {
        this.instructions = instructions;
        numberOfInstructions = instructions.size();
        this.operandForwarding = operandForwarding;
        for (Instruction i : instructions) {
            printMatrix.add(i.getId(), new ArrayList<>());
            printMatrix.get(i.getId()).add(i.toString());
        }
    }

    private void writeBack() {
        Instruction instruction = null;
        if (!accessed.isEmpty()) {
            instruction = accessed.pollFirst();
            System.out.println(instruction + " write back");
            printMatrix.get(instruction.getId()).add(currentCycle, "WB");
            finished.addLast(instruction);
        }
        memoryAccess();
        if (!operandForwarding && instruction != null) {
            instruction.setRegistersBusy(false);
            Instruction forwardedInstruction = fetched.peekFirst();
            if (forwardedInstruction != null) {
                System.out.println(instruction + " writeBack -> forwards operand to -> " + forwardedInstruction + " decode");
            }
        }
    }

    private void memoryAccess() {

        if (!executed.isEmpty()) {
            Instruction instruction = executed.peekFirst();
            System.out.println(instruction + " memory access");
            printMatrix.get(instruction.getId()).add(currentCycle, "MEM");
            if (instruction.getMemCurrentCycle() < instruction.getMemCycles()) {
                instruction.updateMemCurrentCycle();
            } else {
                accessed.addLast(executed.pollFirst());
                instruction.resetMemCurrentCycle();
            }
        }
        execute();
    }

    private void execute() {

        Instruction instruction = null;
        if (!decoded.isEmpty()) {
            instruction = decoded.peekFirst();
            System.out.println(instruction + " execute");
            printMatrix.get(instruction.getId()).add(currentCycle, "EX");
            if (instruction.getExCurrentCycle() < instruction.getExCycles()) {
                instruction.updateExCurrentCycle();
            } else {
                executed.addLast(decoded.pollFirst());
                instruction.resetExCurrentCycle();
            }
        }
        decode();
        if (operandForwarding && instruction != null && instruction.getExCurrentCycle() == 0) {
            instruction.setRegistersBusy(false);
            Instruction forwardedInstruction = fetched.peekFirst();
            if (forwardedInstruction != null) {
                System.out.println(instruction + " execute -> forwards operand to -> " + forwardedInstruction + " decode");
            }
        }
    }

    private void decode() {

        if (!fetched.isEmpty()) {
            Instruction instruction = fetched.pollFirst();
            if (instruction.isDecodable()) {
                System.out.println(instruction + " decode");
                instruction.setRegistersBusy(true);
                decoded.addLast(instruction);
                printMatrix.get(instruction.getId()).add(currentCycle, "ID");
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

    private void fetch() {

        if (!instructions.isEmpty()) {
            Instruction instruction = instructions.pollFirst();
            System.out.println(instruction + " fetch");
            fetched.addLast(instruction);
            printMatrix.get(instruction.getId()).add(currentCycle, "IF");
        }
    }

    private void detectHazard(Instruction instruction1, Instruction instruction2) {

        if (instruction1.getDestination().equals(instruction2.getDestination())) {
            System.out.println("Write After Write hazard detected");
            return;
        }

        if (instruction1 instanceof UnaryInstruction unaryInstruction) {
            if (unaryInstruction.getSource().equals(instruction2.getDestination())) {
                System.out.println("Read After Write Hazard detected");
            } else if (instruction2 instanceof UnaryInstruction unaryInstruction2 && unaryInstruction2.getSource().equals(unaryInstruction.getDestination())) {
                System.out.println("Write After Read hazard detected");
            } else if (instruction2 instanceof BinaryInstruction binaryInstruction2 && (binaryInstruction2.getSource1().equals(unaryInstruction.getDestination()) || binaryInstruction2.getSource2().equals(unaryInstruction.getDestination()))) {
                System.out.println("Write After Read hazard detected");
            } else {
                System.out.println("None hazard detected");
            }
        } else {
            BinaryInstruction binaryInstruction = (BinaryInstruction) instruction1;
            if (binaryInstruction.getSource1().equals(instruction2.getDestination()) || binaryInstruction.getSource2().equals(instruction2.getDestination())) {
                System.out.println("Read After Write Hazard detected");
            } else if (instruction2 instanceof UnaryInstruction unaryInstruction2 && unaryInstruction2.getSource().equals(binaryInstruction.getDestination())) {
                System.out.println("Write After Read hazard detected");
            } else if (instruction2 instanceof BinaryInstruction binaryInstruction2 && (binaryInstruction2.getSource1().equals(binaryInstruction.getDestination()) || binaryInstruction2.getSource2().equals(binaryInstruction.getDestination()))) {
                System.out.println("Write After Read hazard detected");
            } else {
                System.out.println("None hazard detected");
            }
        }
    }

    public void start() {

        addRow();
        System.out.println("Ciklus " + currentCycle);
        fetch();
        currentCycle++;

        addRow();
        System.out.println("Ciklus " + currentCycle);
        decode();
        currentCycle++;

        addRow();
        System.out.println("Ciklus " + currentCycle);
        execute();
        currentCycle++;

        addRow();
        System.out.println("Ciklus " + currentCycle);
        memoryAccess();
        currentCycle++;

        addRow();
        System.out.println("Ciklus " + currentCycle);
        writeBack();
        currentCycle++;

        while (finished.size() != numberOfInstructions) {
            addRow();
            System.out.println("Ciklus " + currentCycle);
            writeBack();
            currentCycle++;

        }

        printMatrix();
    }

    private void addRow() {
        for (ArrayList<String> array : printMatrix)
            array.add(currentCycle, "");
    }

    private void printMatrix() {
        System.out.println();
        System.out.println();
        System.out.printf("%-20s", "Ciklus");
        for (int i = 1; i < currentCycle; i++)
            System.out.printf("%-5s", i);
        System.out.println("\n");
        for (ArrayList<String> array : printMatrix) {
            for (int i = 0; i < array.size(); i++) {
                if (i == 0)
                    System.out.printf("%-20s", array.get(i));
                else
                    System.out.printf("%-5s", array.get(i));
            }
            System.out.println();
        }
    }
}
