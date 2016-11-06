package View.Commands;

/**
 * Created by bnorbert on 06.11.2016.
 * bnorbertcristian@gmail.com
 */
public class ExitCommand extends Command {

    public ExitCommand(String key,String description){
        super(key,description);
    }
    @Override
    public void execute(){
        System.exit(0);
    }
}
