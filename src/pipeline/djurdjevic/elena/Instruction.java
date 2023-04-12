package pipeline.djurdjevic.elena;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class Instruction {

    private int cycles;
    private int currentCycle = 0;
    private String name;
    private ArrayList<Register> registers = new ArrayList<>();

    public Instruction(String name, int cycles){
        this.name = name;
        this.cycles = cycles;
    }
    public void updateCurrentCycle(){
        currentCycle++;
    }
    public boolean isExecutable(){
        return registers.stream().allMatch(Register::isBusy);
    }
}
