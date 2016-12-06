import Collections.*;
import Controller.ProgramController;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.ReadHeapExpression;
import Model.Expressions.VariableExpression;
import Model.FileStatements.CloseFileStatement;
import Model.FileStatements.OpenFileStatement;
import Model.FileStatements.ReadFileStatement;
import Model.HeapStatements.NewStatement;
import Model.HeapStatements.WriteHeapStatement;
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
                        new ArithmeticExpression(new ConstantExpression(2), new ConstantExpression(2), "-")), new CompoundStatement(new IfStatement(new VariableExpression("b"), new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("v", new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v"))))));

        IToyStack<IStatement> executionStack1 = new ToyStack<IStatement>();
        executionStack1.push(program1);
        ProgramState state1 = new ProgramState(executionStack1, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        // 2 --------------------------------------------------
        IStatement program2 = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ConstantExpression(5), new ArithmeticExpression(
                new ConstantExpression(7), new ConstantExpression(2), "*"), "+")), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                new VariableExpression("a"), new ArithmeticExpression(new ConstantExpression(33), new ConstantExpression(2), "/"), "+")),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), "+"))));


        IToyStack<IStatement> executionStack2 = new ToyStack<>();
        executionStack2.push(program2);
        ProgramState state2 = new ProgramState(executionStack2, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        // 3 ---------------------------------------------------
        IStatement program3 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new ConstantExpression(3)),
                        new AssignmentStatement("c", new ArithmeticExpression(new VariableExpression("a"), new ConstantExpression(0), "/"))));

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
                                        "+"),
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

        // 6
        IStatement program6=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(10)),
                new CompoundStatement(
                        new NewStatement("v",new ConstantExpression(20)),
                        new CompoundStatement(
                                new NewStatement("a",new ConstantExpression(20)),
                                new PrintStatement(new VariableExpression("v"))
                        )));
        IToyStack<IStatement> executionStack6=new ToyStack<>();
        executionStack6.push(program6);
        ProgramState state6=new ProgramState(executionStack6,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable());

        // 7
        IStatement program7=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(10)),
                new CompoundStatement(
                        new NewStatement("v",new ConstantExpression(20)),
                        new CompoundStatement(
                                new NewStatement("a",new ConstantExpression(22)),
                                new CompoundStatement(
                                        new PrintStatement(new ArithmeticExpression(new ConstantExpression(100),new ReadHeapExpression("v"),"+")),
                                        new PrintStatement(new ArithmeticExpression(new ConstantExpression(100),new ReadHeapExpression("a"),"+")))
                        )
                )
        );
        IToyStack<IStatement> executionStack7=new ToyStack<>();
        executionStack7.push(program7);
        ProgramState state7=new ProgramState(executionStack7,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable());

        // 8
        IStatement program8=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(10)),
                new CompoundStatement(
                        new NewStatement("v",new ConstantExpression(20)),
                        new CompoundStatement(
                                new NewStatement("a",new ConstantExpression(22)),
                                new CompoundStatement(
                                        new WriteHeapStatement("a",new ConstantExpression(30)),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("a")),
                                                new PrintStatement(new ReadHeapExpression("a"))
                                        )
                                )
                        )
                )
        );
        IToyStack<IStatement> executionStack8=new ToyStack<>();
        executionStack8.push(program8);
        ProgramState state8=new ProgramState(executionStack8,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable());

        // 9
        IStatement program9=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(10)),
                new CompoundStatement(
                        new NewStatement("v",new ConstantExpression(20)),
                        new CompoundStatement(
                                new NewStatement("a",new ConstantExpression(22)),
                                new CompoundStatement(
                                        new WriteHeapStatement("a",new ConstantExpression(30)),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("a")),
                                                new CompoundStatement(
                                                        new PrintStatement(new ReadHeapExpression("a")),
                                                        new AssignmentStatement("a",new ConstantExpression(0))
                                                )
                                        )
                                )
                        )
                )
        );
        IToyStack<IStatement> executionStack9=new ToyStack<>();
        executionStack9.push(program9);
        ProgramState state9=new ProgramState(executionStack9,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable());

        // 10
        IStatement program10=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(6)),
                new CompoundStatement(
                        new WhileStatement(new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(4),"-"),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(1),"-"))
                                )),
                        new PrintStatement(new VariableExpression("v"))
                )
        );
        IToyStack<IStatement> executionStack10=new ToyStack<>();
        executionStack10.push(program10);
        ProgramState state10=new ProgramState(executionStack10,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable());


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

            IStateRepository repo6=new StateRepository();
            repo6.setLogFile("log6.txt");
            ProgramController ctrl6=new ProgramController(repo6);
            ctrl6.addProgram(state6);

            IStateRepository repo7=new StateRepository();
            repo7.setLogFile("log7.txt");
            ProgramController ctrl7=new ProgramController(repo7);
            ctrl7.addProgram(state7);

            IStateRepository repo8=new StateRepository();
            repo8.setLogFile("log8.txt");
            ProgramController ctrl8=new ProgramController(repo8);
            ctrl8.addProgram(state8);

            IStateRepository repo9=new StateRepository();
            repo9.setLogFile("log9.txt");
            ProgramController ctrl9=new ProgramController(repo9);
            ctrl9.addProgram(state9);

            IStateRepository repo10=new StateRepository();
            repo10.setLogFile("log10.txt");
            ProgramController ctrl10=new ProgramController(repo10);
            ctrl10.addProgram(state10);



            TextMenu menu=new TextMenu();

            menu.addCommand(new RunExampleCommand("10",program10.toString(),ctrl10));
            menu.addCommand(new RunExampleCommand("9",program9.toString(),ctrl9));
            menu.addCommand(new RunExampleCommand("8",program8.toString(),ctrl8));
            menu.addCommand(new RunExampleCommand("7",program7.toString(),ctrl7));
            menu.addCommand(new RunExampleCommand("6",program6.toString(),ctrl6));
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
