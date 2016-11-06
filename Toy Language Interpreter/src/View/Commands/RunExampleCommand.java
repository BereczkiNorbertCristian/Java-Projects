package View.Commands;

import Controller.ProgramController;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class RunExampleCommand extends Command {

    private ProgramController ctrl;

    public RunExampleCommand(String key,String description,ProgramController ctrl){
        super(key,description);
        this.ctrl=ctrl;
    }
    @Override
    public void execute(){
        try{
            ctrl.allStep();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
