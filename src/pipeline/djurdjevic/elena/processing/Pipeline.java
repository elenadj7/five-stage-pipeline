package pipeline.djurdjevic.elena.processing;

import pipeline.djurdjevic.elena.instructions.Instruction;

import java.util.LinkedList;

public class Pipeline {

    private LinkedList<Instruction> instructions = new LinkedList<>();
    private LinkedList<Instruction> fetched = new LinkedList<>();
    private LinkedList<Instruction> decoded = new LinkedList<>();
    private LinkedList<Instruction> executed = new LinkedList<>();
    private LinkedList<Instruction> accessed = new LinkedList<>();

    public Pipeline(){}

    public void writeBack(){
        if(!accessed.isEmpty()){
            Instruction instruction = accessed.pollFirst();
            System.out.println(instruction + " write back");
        }
        memoryAccess();
    }
    public void memoryAccess(){

        if(!executed.isEmpty()){
            Instruction instruction = executed.peekFirst();
            System.out.println(instruction + " memory access");
            if(instruction.getMemCurrentCycle() < instruction.getMemCycles()){
                instruction.updateMemCurrentCycle();
            }
            else {
                accessed.addLast(executed.pollFirst());
            }
        }
        execute();
    }
    public void execute(){

        if(!decoded.isEmpty()){
            Instruction instruction = decoded.peekFirst();
            System.out.println(instruction + " execute");
            if(instruction.getExCurrentCycle() < instruction.getExCycles()){
                instruction.updateExCurrentCycle();
            }
            else {
                executed.addLast(decoded.pollFirst());
            }
        }
        decode();
    }
    public void decode(){

        if(!fetched.isEmpty()){
            Instruction instruction = fetched.peekFirst();
            if(instruction.isDecodable()){
                System.out.println(instruction + " decode");
                instruction.getRegisters().forEach(i -> i.isBusy(true));
            }
        }

        fetch();
    }
    public void fetch(){

        if(!instructions.isEmpty()){
            Instruction instruction = instructions.pollFirst();
            System.out.println(instruction + " fetch");
            fetched.addLast(instruction);
        }
    }
}
