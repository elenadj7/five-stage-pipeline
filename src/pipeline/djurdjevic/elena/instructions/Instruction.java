package pipeline.djurdjevic.elena.instructions;

import pipeline.djurdjevic.elena.processing.Register;

import java.util.ArrayList;

public abstract class Instruction {

    private static int instance = 0;

    private int id;
    private int exCycles;
    private int memCycles;
    private int exCurrentCycle = 0;
    private int memCurrentCycle = 0;
    private String name;
    private ArrayList<Register> registers = new ArrayList<>();

    public Instruction(String name, int exCycles, int memCycles) {
        this.name = name;
        this.exCycles = exCycles;
        this.memCycles = memCycles;
        id = ++instance;
    }
    public int getId() {
        return id;
    }

    public void updateExCurrentCycle() {exCurrentCycle++;}
    public void updateMemCurrentCycle(){memCurrentCycle++;}
    public int getExCycles(){return exCycles;}
    public int getMemCurrentCycle(){return memCurrentCycle;}
    public int getExCurrentCycle(){return exCurrentCycle;}
    public int getMemCycles(){return memCycles;}
    public boolean isDecodable() {return registers.stream().allMatch(Register::isNotBusy);}
    public ArrayList<Register> getRegisters (){return registers;}

    @Override
    public String toString() {
        return name + id;
    }
}
