package sample;

import Collections.*;
import Controller.ProgramController;
import Model.EntryProgram;
import Model.Expressions.ArithmeticExpression;
import Model.Expressions.ConstantExpression;
import Model.Expressions.ReadHeapExpression;
import Model.Expressions.VariableExpression;
import Model.FileStatements.CloseFileStatement;
import Model.FileStatements.OpenFileStatement;
import Model.FileStatements.ReadFileStatement;
import Model.HeapStatements.NewStatement;
import Model.HeapStatements.WriteHeapStatement;
import Model.LockStatements.LockStatement;
import Model.LockStatements.NewLockStatement;
import Model.LockStatements.UnlockStatement;
import Model.ProgramState;
import Model.Statements.*;
import Repository.IStateRepository;
import Repository.StateRepository;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bnorbert on 13.01.2017.
 * bnorbertcristian@gmail.com
 */
public class SecondaryController {

    private VBox secondaryRoot;
    private Stage secondaryStage;
    private List<EntryProgram> programs;
    private ListView<EntryProgram> programStatesView;
    private Button chooseProgramButton;
    private ProgramController programController;


    public SecondaryController(Stage secondaryStage){
        this.secondaryStage=secondaryStage;
        chooseProgramButton = new Button("CHOOSE");
    }

    private void initSecondaryGUI(){

        secondaryRoot = new VBox(20);
        secondaryStage.setTitle("Choose Program");
        secondaryStage.setScene(new Scene(secondaryRoot,800,600));

        programStatesView = new ListView<>(FXCollections.observableArrayList(programs));
        programStatesView.getSelectionModel().select(0);

        secondaryRoot.getChildren().add(new Label("Choose one of the programs:"));
        secondaryRoot.getChildren().add(programStatesView);
        secondaryRoot.getChildren().add(chooseProgramButton);

    }

    public void startSecondaryStage(){

        initData();
        initSecondaryGUI();
        readyButton();
        secondaryStage.show();

    }

    private void readyButton(){
        chooseProgramButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EntryProgram entryProgram = programStatesView.getSelectionModel().getSelectedItem();
                programController = entryProgram.getCtrl();
                secondaryStage.hide();
            }
        });
    }

    public ProgramController getProgramController(){
        return programController;
    }

    private void initData(){

        // 1 --------------------------------------------------
        IStatement program1 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new VariableExpression("a")), new CompoundStatement(new AssignmentStatement("b",
                        new ArithmeticExpression(new ConstantExpression(2), new ConstantExpression(2), "-")), new CompoundStatement(new IfStatement(new VariableExpression("b"), new AssignmentStatement("v", new ConstantExpression(2)),
                        new AssignmentStatement("v", new ConstantExpression(3))),
                        new PrintStatement(new VariableExpression("v"))))));

        IToyStack<IStatement> executionStack1 = new ToyStack<>();
        executionStack1.push(program1);
        ProgramState state1 = new ProgramState(executionStack1, new ToyMap<String, Integer>(), new ToyList<>(),new FileTable(),1);

        // 2 --------------------------------------------------
        IStatement program2 = new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ConstantExpression(5), new ArithmeticExpression(
                new ConstantExpression(7), new ConstantExpression(2), "*"), "+")), new CompoundStatement(new AssignmentStatement("b", new ArithmeticExpression(
                new VariableExpression("a"), new ArithmeticExpression(new ConstantExpression(33), new ConstantExpression(2), "/"), "+")),
                new PrintStatement(new ArithmeticExpression(new VariableExpression("a"), new VariableExpression("b"), "+"))));


        IToyStack<IStatement> executionStack2 = new ToyStack<>();
        executionStack2.push(program2);
        ProgramState state2 = new ProgramState(executionStack2, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable(),2);

        // 3 ---------------------------------------------------
        IStatement program3 = new CompoundStatement(new AssignmentStatement("a", new ConstantExpression(3)),
                new CompoundStatement(new PrintStatement(new ConstantExpression(3)),
                        new AssignmentStatement("c", new ArithmeticExpression(new VariableExpression("a"), new ConstantExpression(0), "/"))));

        IToyStack<IStatement> executionStack3 = new ToyStack<IStatement>();
        executionStack3.push(program3);
        ProgramState state3 = new ProgramState(executionStack3, new ToyMap<String,Integer>(), new ToyList<String>(),new FileTable(),3);

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
        ProgramState state4=new ProgramState(executionStack4,new ToyMap<String,Integer>(),new ToyList<String>(),new FileTable(),4);

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
        ProgramState state5=new ProgramState(executionStack5,new ToyMap<String,Integer>(),new ToyList<String>(),new FileTable(),5);

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
        ProgramState state6=new ProgramState(executionStack6,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),6);

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
        ProgramState state7=new ProgramState(executionStack7,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),7);

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
        ProgramState state8=new ProgramState(executionStack8,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),8);

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
        ProgramState state9=new ProgramState(executionStack9,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),9);

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
        ProgramState state10=new ProgramState(executionStack10,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),10);

        // 11
        IStatement program11=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(6)),
                new CompoundStatement(
                        new NewStatement("d",new ConstantExpression(10)),
                        new CompoundStatement(
                                new WhileStatement(new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(4),"-"),
                                        new CompoundStatement(
                                                new PrintStatement(new VariableExpression("v")),
                                                new AssignmentStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(1),"-"))
                                        )),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new ArithmeticExpression(new ConstantExpression(100),new ReadHeapExpression("d"),"+"))
                                )
                        )
                )
        );
        IToyStack<IStatement> executionStack11=new ToyStack<>();
        executionStack11.push(program11);
        ProgramState state11=new ProgramState(executionStack11,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),11);

        // 12
        IStatement program12=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(10)),
                new CompoundStatement(
                        new NewStatement("a",new ConstantExpression(22)),
                        new CompoundStatement(
                                new ForkStatement(
                                        new CompoundStatement(
                                                new WriteHeapStatement("a",new ConstantExpression(30)),
                                                new CompoundStatement(
                                                        new AssignmentStatement("v",new ConstantExpression(32)),
                                                        new CompoundStatement(
                                                                new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeapExpression("a"))
                                                        )
                                                )
                                        )),
                                new CompoundStatement(
                                        new PrintStatement(new VariableExpression("v")),
                                        new PrintStatement(new ReadHeapExpression("a"))
                                )
                        )
                )
        );
        IToyStack<IStatement> executionStack12= new ToyStack<>();
        executionStack12.push(program12);
        ProgramState state12=new ProgramState(executionStack12,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),12);

        //13 practic
        IStatement program13=new CompoundStatement(
                new AssignmentStatement("v",new ConstantExpression(20)),
                new CompoundStatement(
                        new ForStatement(
                                "v",
                                new ConstantExpression(0),
                                new ConstantExpression(3),
                                new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(1),"+"),
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v",new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(1),"+"))))),
                        new PrintStatement(new ArithmeticExpression(new VariableExpression("v"),new ConstantExpression(10),"*"))
                )
        );
        IToyStack<IStatement> executionStack13= new ToyStack<>();
        executionStack13.push(program13);
        ProgramState state13=new ProgramState(executionStack13,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),13);

        //14 practic
        IStatement program14=new CompoundStatement(
                new NewStatement("v1",new ConstantExpression(20)),
                new CompoundStatement(
                        new NewStatement("v2",new ConstantExpression(30)),
                        new CompoundStatement(
                                new NewLockStatement("x"),
                                new CompoundStatement(
                                        new ForkStatement(
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new LockStatement("x"),
                                                                        new CompoundStatement(
                                                                            new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression("v1"),new ConstantExpression(1),"-")),
                                                                                new UnlockStatement("x")
                                                                        )
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new LockStatement("x"),
                                                                new CompoundStatement(
                                                                        new WriteHeapStatement("v1",new ArithmeticExpression(new ReadHeapExpression("v1"),new ConstantExpression(1),"+")),
                                                                        new UnlockStatement("x")
                                                                )
                                                        )
                                                )
                                        ),
                                        new CompoundStatement(
                                                new NewLockStatement("q"),
                                                new CompoundStatement(
                                                        new ForkStatement(
                                                                new CompoundStatement(
                                                                        new ForkStatement(
                                                                            new CompoundStatement(
                                                                                    new LockStatement("q"),
                                                                                    new CompoundStatement(
                                                                                        new WriteHeapStatement("v2",new ArithmeticExpression(new ReadHeapExpression("v2"),new ConstantExpression(5),"+")),
                                                                                        new UnlockStatement("q")
                                                                                    )
                                                                            )
                                                                        ),
                                                                        new CompoundStatement(
                                                                                new AssignmentStatement("m",new ConstantExpression(100)),
                                                                                new CompoundStatement(
                                                                                        new LockStatement("q"),
                                                                                        new CompoundStatement(
                                                                                                new WriteHeapStatement("v2",new ArithmeticExpression(new ReadHeapExpression("v2"),new ConstantExpression(1),"+")),
                                                                                                new UnlockStatement("q")
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        ),
                                                        new CompoundStatement(
                                                                new AssignmentStatement("z",new ConstantExpression(200)),
                                                                new CompoundStatement(
                                                                        new AssignmentStatement("z",new ConstantExpression(300)),
                                                                        new CompoundStatement(
                                                                                new AssignmentStatement("z",new ConstantExpression(400)),
                                                                                new CompoundStatement(
                                                                                        new LockStatement("x"),
                                                                                        new CompoundStatement(
                                                                                                new PrintStatement(new ReadHeapExpression("v1")),
                                                                                                new CompoundStatement(
                                                                                                        new UnlockStatement("x"),
                                                                                                        new CompoundStatement(
                                                                                                                new LockStatement("q"),
                                                                                                                new CompoundStatement(
                                                                                                                        new PrintStatement(new ReadHeapExpression("v2")),
                                                                                                                        new UnlockStatement("q")
                                                                                                                )
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                )
                                                                        )
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
        IToyStack<IStatement> executionStack14= new ToyStack<>();
        executionStack14.push(program14);
        ProgramState state14=new ProgramState(executionStack14,new ToyMap<String, Integer>(),new ToyList<String>(),new FileTable(),14);
        ILockTable lockTable14=new LockTable();
        state14.setLockTable(lockTable14);



        try {
            IStateRepository repo1 = new StateRepository();
            repo1.setLogFile("log1.txt");
            ProgramController ctrl1 = new ProgramController(repo1);
            ctrl1.addProgram(state1);

            IStateRepository repo2 = new StateRepository();
            repo2.setLogFile("log2.txt");
            ProgramController ctrl2 = new ProgramController(repo2);
            ctrl2.addProgram(state2);

            IStateRepository repo3 = new StateRepository();
            repo3.setLogFile("log3.txt");
            ProgramController ctrl3 = new ProgramController(repo3);
            ctrl3.addProgram(state3);

            IStateRepository repo4 = new StateRepository();
            repo4.setLogFile("log4.txt");
            ProgramController ctrl4 = new ProgramController(repo4);
            ctrl4.addProgram(state4);

            IStateRepository repo5 = new StateRepository();
            repo5.setLogFile("log5.txt");
            ProgramController ctrl5 = new ProgramController(repo5);
            ctrl5.addProgram(state5);

            IStateRepository repo6 = new StateRepository();
            repo6.setLogFile("log6.txt");
            ProgramController ctrl6 = new ProgramController(repo6);
            ctrl6.addProgram(state6);

            IStateRepository repo7 = new StateRepository();
            repo7.setLogFile("log7.txt");
            ProgramController ctrl7 = new ProgramController(repo7);
            ctrl7.addProgram(state7);

            IStateRepository repo8 = new StateRepository();
            repo8.setLogFile("log8.txt");
            ProgramController ctrl8 = new ProgramController(repo8);
            ctrl8.addProgram(state8);

            IStateRepository repo9 = new StateRepository();
            repo9.setLogFile("log9.txt");
            ProgramController ctrl9 = new ProgramController(repo9);
            ctrl9.addProgram(state9);

            IStateRepository repo10 = new StateRepository();
            repo10.setLogFile("log10.txt");
            ProgramController ctrl10 = new ProgramController(repo10);
            ctrl10.addProgram(state10);

            IStateRepository repo11 = new StateRepository();
            repo11.setLogFile("log11.txt");
            ProgramController ctrl11 = new ProgramController(repo11);
            ctrl11.addProgram(state11);

            IStateRepository repo12 = new StateRepository();
            repo12.setLogFile("log12.txt");
            ProgramController ctrl12 = new ProgramController(repo12);
            ctrl12.addProgram(state12);

            IStateRepository repo13 = new StateRepository();
            repo13.setLogFile("log13.txt");
            ProgramController ctrl13 = new ProgramController(repo13);
            ctrl13.addProgram(state13);

            IStateRepository repo14 = new StateRepository();
            repo14.setLogFile("log14.txt");
            ProgramController ctrl14 = new ProgramController(repo14);
            ctrl14.addProgram(state14);



            programs=new ArrayList<>();

            programs.add(new EntryProgram(1,state1,ctrl1));
            programs.add(new EntryProgram(2,state2,ctrl2));
            programs.add(new EntryProgram(3,state3,ctrl3));
            programs.add(new EntryProgram(4,state4,ctrl4));
            programs.add(new EntryProgram(5,state5,ctrl5));
            programs.add(new EntryProgram(6,state6,ctrl6));
            programs.add(new EntryProgram(7,state7,ctrl7));
            programs.add(new EntryProgram(8,state8,ctrl8));
            programs.add(new EntryProgram(9,state9,ctrl9));
            programs.add(new EntryProgram(10,state10,ctrl10));
            programs.add(new EntryProgram(11,state11,ctrl11));
            programs.add(new EntryProgram(12,state12,ctrl12));
            programs.add(new EntryProgram(13,state13,ctrl13));
            programs.add(new EntryProgram(14,state14,ctrl14));







        }
        catch (Exception e){
            System.out.println("Not cool, you got an error" + e.getMessage());
        }
    }

}
