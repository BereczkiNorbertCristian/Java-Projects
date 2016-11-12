package View;

import Collections.*;
import Controller.ProgramController;
import Exceptions.ProgramControllerException;
import Model.*;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.VariableExpression;
import Model.Statements.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by bnorbert on 28.10.2016.
 * bnorbertcristian@gmail.com
 */
public class View {

    ProgramController ctrl;
    Scanner inputReader;
    List<ProgramState> programs;

    public View(ProgramController ctrl) {
        this.ctrl = ctrl;
        inputReader = new Scanner(System.in);
        programs = new ArrayList<>();
    }

    private void setUp() {

        IStatement program1 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new CompoundStatement(new AssignmentStatement("b",
                        new ArithmeticExpression(new ConstantExpression(2), new ConstantExpression(2), 2)), new CompoundStatement(new IfStatement(new VariableExpression("b"), new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("v", new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v"))))));

        IStatement program2 = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ConstantExpression(5), new ArithmeticExpression(
                new ConstantExpression(7), new ConstantExpression(2), 3), 1)), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                new VariableExpression("a"), new ArithmeticExpression(new ConstantExpression(33), new ConstantExpression(2), 4), 1)),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), 1))));

        IStatement program3 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new ConstantExpression(3)),
                        new AssignmentStatement("c", new ArithmeticExpression(new VariableExpression("a"), new ConstantExpression(0), 4))));

        IToyStack<IStatement> executionStack1 = new ToyStack<IStatement>();
        executionStack1.push(program1);
        ProgramState state1 = new ProgramState(executionStack1, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        IToyStack<IStatement> executionStack2 = new ToyStack<IStatement>();
        executionStack2.push(program2);
        ProgramState state2 = new ProgramState(executionStack2, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        IToyStack<IStatement> executionStack3 = new ToyStack<IStatement>();
        executionStack3.push(program3);
        ProgramState state3 = new ProgramState(executionStack3, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable());

        programs.add(state1);
        programs.add(state2);
        programs.add(state3);

    }


    public void run() {


        setUp();

        System.out.print("Introduce log file path:");
        String logFilePath=inputReader.next();
        ctrl.setLogFile(logFilePath);

        boolean cool = false;
        while (!cool) {
            System.out.print(preMenu());
            String program = inputReader.next();
            cool = true;
            switch (program) {
                case "1":
                    ctrl.addProgram(programs.get(0));

                    break;
                case "2":
                    ctrl.addProgram(programs.get(1));
                    break;
                case "3":
                    ctrl.addProgram(programs.get(2));
                    break;
                default:
                    cool = false;
                    System.out.println("Please introduce an option between 1,2,3");
                    break;
            }
        }
        ctrl.setCurrentProgram(0);
        int option;
        try {
            while (true) {
                System.out.print(menu());
                option = inputReader.nextInt();
                if (option == 1) {
                    ctrl.oneStepCurrentProgram();
                    System.out.println(ctrl.getCurrentProgram().toString());
                } else {
                    ctrl.allStep();
                }
            }
        } catch (ProgramControllerException e) {
            if(ctrl.getProgramOuput().length() != 0){
                System.out.println(ctrl.getProgramOuput());
            }
            System.out.println(e.getMessage().toUpperCase());
        }

        showOutput();

        System.out.println("PROGRAM TERMINATED");



    }

    private String preMenu() {

        String menu = "";

        menu = "Choose your program:" +
                "1." +
                "a=3;" +
                "print(a);" +
                "b=2-2;" +
                "If(b) then v=2;" +
                "else v=3;" +
                "print(v)" +
                "\n" +
                "2." +
                "a=5+7*2;" +
                "b=a+33/2;" +
                "print(a+b)" +
                "\n" +
                "3." +
                "a=3;" +
                "print(3);" +
                "c=a/0" +
                "\n" +
                "Your option(let it be int): ";

        return menu;
    }

    private void showOutput() {

        System.out.println("Output of the program:");
        IToyList<String> outputList = ctrl.getCurrentProgramOutput();
        for (int i = 0; i < outputList.size(); i++) {
            System.out.println(outputList.get(i));
        }
    }

    private String menu() {

        String menu = "";

        menu = "Choose your option:" +
                "1.One step execution" +
                "2.All step execution" +
                "Your option(please integer):";
        return menu;
    }

}
