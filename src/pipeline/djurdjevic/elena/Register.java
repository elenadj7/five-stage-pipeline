package pipeline.djurdjevic.elena;

public class Register {

    private boolean isBusy;
    private final String name;

    public Register(String name){
        this.name = name;
        isBusy = false;
    }
    public void isBusy(boolean busy){
        isBusy = busy;
    }
    public boolean isBusy(){
        return isBusy;
    }
    public String getName(){
        return name;
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Register register){
            return register.getName().equals(this.name);
        }
        return false;
    }
}
