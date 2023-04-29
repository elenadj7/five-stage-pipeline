package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

import java.util.ArrayList;

public abstract class Instruction {

    private static int instance = 0;

    protected int id;
    protected int exCycles;
    protected int memCycles;
    protected int exCurrentCycle = 0;
    protected int memCurrentCycle = 0;
    protected String name;
    protected Register destination;
    protected ArrayList<Register> registers = new ArrayList<>();

    public Instruction(String name, int exCycles, int memCycles, Register destination) {
        this.name = name;
        this.exCycles = exCycles;
        this.memCycles = memCycles;
        this.destination = destination;
        registers.add(destination);
        id = instance++;
    }

    public int getId() {
        return id;
    }

    public void updateExCurrentCycle() {
        exCurrentCycle++;
    }

    public void updateMemCurrentCycle() {
        memCurrentCycle++;
    }

    public int getExCycles() {
        return exCycles;
    }

    public int getMemCurrentCycle() {
        return memCurrentCycle;
    }

    public int getExCurrentCycle() {
        return exCurrentCycle;
    }

    public int getMemCycles() {
        return memCycles;
    }

    public boolean isDecodable() {
        return registers.stream().allMatch(Register::isNotBusy);
    }

    public ArrayList<Register> getRegisters() {
        return registers;
    }

    public void resetExCurrentCycle() {
        exCurrentCycle = 0;
    }

    public void resetMemCurrentCycle() {
        memCurrentCycle = 0;
    }

    public void setRegistersBusy(boolean busy) {
        registers.forEach(r -> r.isBusy(busy));
    }

    public Register getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return name + id;
    }
}
