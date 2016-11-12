import Collections.*;
import Controller.ProgramController;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.VariableExpression;
import Model.FileStatements.CloseFileStatement;
import Model.FileStatements.OpenFileStatement;
import Model.FileStatements.ReadFileStatement;
import Model.ProgramState;
import Model.Statements.*;
import Repository.IStateRepository;
import Repository.StateRepository;
import View.Commands.ExitCommand;
import View.Commands.RunExampleCommand;
import View.TextMenu;

/**
 * Created by bnorbert on 14.10.2016.
 */
public class Interpreter {

    public static void main(String[] args) {

        // 1 --------------------------------------------------
        IStatement program1 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new CompoundStatement(new AssignmentStatement("b",
                        new ArithmeticExpression(new ConstantExpression(2), new ConstantExpression(2), 2)), new CompoundStatement(new IfStatement(new VariableExpression("b"), new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("v", new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v"))))));

        IToyStack<IStatement> executionStack1 = new ToyStack<IStatement>();
        executionStack1.push(program1);
        ProgramState state1 = new ProgramState(executionStack1, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        // 2 --------------------------------------------------
        IStatement program2 = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ConstantExpression(5), new ArithmeticExpression(
                new ConstantExpression(7), new ConstantExpression(2), 3), 1)), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                new VariableExpression("a"), new ArithmeticExpression(new ConstantExpression(33), new ConstantExpression(2), 4), 1)),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 1))));


        IToyStack<IStatement> executionStack2 = new ToyStack<>();
        executionStack2.push(program2);
        ProgramState state2 = new ProgramState(executionStack2, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        // 3 ---------------------------------------------------
        IStatement program3 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new ConstantExpression(3)),
                        new AssignmentStatement("c", new ArithmeticExpression(new VariableExpression("a"), new ConstantExpression(0), 4))));

        IToyStack<IStatement> executionStack3 = new ToyStack<IStatement>();
        executionStack3.push(program3);
        ProgramState state3 = new ProgramState(executionStack3, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        // 4
        IStatement program4 = new CompoundStatement(new OpenFileStatement("var_f","test.in"),
                new CompoundStatement(
                        new ReadFileStatement(new VariableExpression("var_f"),"var_c"),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("var_c")),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("var_c"),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("var_f"),"var_c"),
                                                        new PrintStatement(new VariableExpression("var_c"))),
                                                new PrintStatement(new ConstantExpression(0)))
                                ,
                                        new CloseFileStatement(new VariableExpression("var_f"))
                                ))));
        IToyStack<IStatement> executionStack4=new ToyStack<>();
        executionStack4.push(program4);
        ProgramState state4=new ProgramState(executionStack4,new ToyMap<String,Integer>(),new ToyList<String>(),new FileTable());

        // 5
        IStatement program5=new CompoundStatement(new OpenFileStatement("var_f","test.in"),
                new CompoundStatement(
                        new ReadFileStatement(
                                new ArithmeticExpression(
                                        new VariableExpression("var_f"),
                                        new ConstantExpression(2),
                                        1),
                                "var_c"),
                        new CompoundStatement(
                                new PrintStatement(new VariableExpression("var_c")),
                                new CompoundStatement(
                                        new IfStatement(
                                                new VariableExpression("var_c"),
                                                new CompoundStatement(
                                                        new ReadFileStatement(new VariableExpression("var_f"),"var_c"),
                                                        new PrintStatement(new VariableExpression("var_c"))),
                                                new PrintStatement(new ConstantExpression(0)))
                                        ,
                                        new CloseFileStatement(new VariableExpression("var_f"))
                                ))));
        IToyStack<IStatement> executionStack5=new ToyStack<>();
        executionStack5.push(program5);
        ProgramState state5=new ProgramState(executionStack5,new ToyMap<String,Integer>(),new ToyList<String>(),new FileTable());


        try {
            IStateRepository repo1 = new StateRepository();
            repo1.setLogFile("log1.txt");
            ProgramController ctrl1=new ProgramController(repo1);
            ctrl1.addProgram(state1);

            IStateRepository repo2 = new StateRepository();
            repo2.setLogFile("log2.txt");
            ProgramController ctrl2=new ProgramController(repo2);
            ctrl2.addProgram(state2);

            IStateRepository repo3 = new StateRepository();
            repo3.setLogFile("log3.txt");
            ProgramController ctrl3=new ProgramController(repo3);
            ctrl3.addProgram(state3);

            IStateRepository repo4 = new StateRepository();
            repo4.setLogFile("log4.txt");
            ProgramController ctrl4=new ProgramController(repo4);
            ctrl4.addProgram(state4);

            IStateRepository repo5 = new StateRepository();
            repo5.setLogFile("log5.txt");
            ProgramController ctrl5=new ProgramController(repo5);
            ctrl5.addProgram(state5);



            TextMenu menu=new TextMenu();

            menu.addCommand(new RunExampleCommand("5",program5.toString(),ctrl5));
            menu.addCommand(new RunExampleCommand("4",program4.toString(),ctrl4));
            menu.addCommand(new RunExampleCommand("3",program3.toString(),ctrl3));
            menu.addCommand(new RunExampleCommand("2",program2.toString(),ctrl2));
            menu.addCommand(new RunExampleCommand("1",program1.toString(),ctrl1));
            menu.addCommand(new ExitCommand("0","exit"));

            menu.show();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
