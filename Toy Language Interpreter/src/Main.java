import Controller.ProgramController;
import Repository.IStateRepository;
import Repository.StateRepository;
import View.View;

/**
 * Created by bnorbert on 14.10.2016.
 */
public class Main {

    public static void main(String[] args) {

        IStateRepository repo = new StateRepository();
        ProgramController ctrl = new ProgramController(repo);
        View view = new View(ctrl);

        view.run();

    }
}