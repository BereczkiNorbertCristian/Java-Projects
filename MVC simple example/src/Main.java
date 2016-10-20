import Controller.Controller;
import Repository.*;
import View.Ui;

/**
 * Created by bnorbert on 09.10.2016.
 */
public class Main {

    public static void main(String[] args){

        ShapeRepository repo=new InMemoryShapeRepository();
        Controller ctrl=new Controller(repo);
        Ui ui=new Ui(ctrl);

        ui.run();

    }

}
