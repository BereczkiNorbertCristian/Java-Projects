package sample;

import Collections.*;
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
import Model.Pair;
import Model.ProgramState;
import Model.Statements.*;
import Repository.IStateRepository;
import Repository.StateRepository;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import Controller.ProgramController;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.util.*;

public class Main extends Application {

    HBox root;
    ListView<String> exeStackView,outView;
    TableView<Pair<String,Integer>> symbolTableView;
    TableView<Pair<Integer,Integer>> heapView;
    TableView<Pair<Integer,Pair<String,BufferedReader>>> fileTableView;
    ListView<EntryProgram> programStatesView;
    TextField noProgramsField;
    Button runOneStepButton;

    List<EntryProgram> programs;


    @Override
    public void start(Stage primaryStage) throws Exception{

        //Main entry of getting ready
        //TO DO:div by 0 exception thrown


        initData();
        initGUI(primaryStage);
        listenOnSelection();
        readyButton();
        primaryStage.show();
    }

    public void readyButton(){

        runOneStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                EntryProgram selectedEntry=programStatesView.getSelectionModel().getSelectedItem();
                int adder=1,idx=programStatesView.getSelectionModel().getSelectedIndices().get(0);
                selectedEntry.getCtrl().allStepGUI();
                if(idx == programs.size()-1){
                    adder=-1;
                }
                programStatesView.getSelectionModel().select(idx+adder);
                programStatesView.getSelectionModel().select(idx);

            }
        });
    }

    public void listenOnSelection(){

        programStatesView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<EntryProgram>() {
                    @Override
                    public void changed(ObservableValue<? extends EntryProgram> observable, EntryProgram oldValue, EntryProgram newValue) {
                        exeStackView.setItems(getExeStackOList(newValue));
                        symbolTableView.setItems(getSymbolTableOList(newValue));
                        outView.setItems(getOutOList(newValue));
                        heapView.setItems(getHeapOList(newValue));
                        fileTableView.setItems(getFileTableOList(newValue));
                    }
                }
        );

    }

    public ObservableList<Pair<Integer,Integer>> getHeapOList(EntryProgram entryProgram){

        ProgramState state=entryProgram.getProgam();
        List<Pair<Integer,Integer>> ret=new ArrayList<>();
        Iterator<Map.Entry<Integer,Integer>> iter=state.getHeap().iterator();
        while (iter.hasNext()){
            Map.Entry<Integer,Integer> entry=iter.next();
            ret.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<Pair<Integer,Pair<String,BufferedReader>>> getFileTableOList(EntryProgram entryProgram){

        ProgramState state=entryProgram.getProgam();
        List<Pair<Integer,Pair<String,BufferedReader>>> ret=new ArrayList<>();
        Iterator<Map.Entry<Integer,Pair<String,BufferedReader>>> iter=state.getFileTable().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer,Pair<String,BufferedReader>> entry=iter.next();
            ret.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return FXCollections.observableArrayList(ret);

    }

    public ObservableList<String> getOutOList(EntryProgram entryProgram){
        ProgramState state=entryProgram.getProgam();
        List<String> ret=new ArrayList<>();
        Iterator<String> iter=state.getOut().iterator();
        while (iter.hasNext()){
            ret.add(iter.next());
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<Pair<String,Integer>> getSymbolTableOList(EntryProgram entryProgram){
        ProgramState state=entryProgram.getProgam();
        List<Pair<String,Integer>> ret=new ArrayList<>();
        Iterator<Map.Entry<String,Integer>> iter = state.getSymbolTable().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> mapEntry=iter.next();
            Pair<String,Integer> pr=new Pair<>(mapEntry.getKey(),mapEntry.getValue());
            ret.add(pr);
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<String> getExeStackOList(EntryProgram entryProgram){

        ProgramState state=entryProgram.getProgam();
        List<String> ret=new ArrayList<>();
        Iterator<IStatement> iter = state.getExecutionStack().iterator();
        while(iter.hasNext()){
            IStatement stmt=iter.next();
            ret.add(stmt.toString());
        }
        return FXCollections.observableArrayList(ret);
    }



    private void initGUI(Stage primaryStage)throws Exception{

        root = new HBox(30);
        root.setPadding(new Insets(30));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 900, 700));

        VBox leftPane,middlePane,rightPane;

        leftPane=new VBox(10);
        middlePane=new VBox(10);
        rightPane=new VBox(10);

        createLeftPane(leftPane);
        createMiddlePane(middlePane);
        createRightPane(rightPane);

        root.getChildren().add(leftPane);
        root.getChildren().add(middlePane);
        root.getChildren().add(rightPane);

    }

    public void createLeftPane(VBox leftPane){

        exeStackView=new ListView<>(FXCollections.observableArrayList());
        symbolTableView = new TableView<>();

        TableColumn<Pair<String,Integer>,String> columnVariable=new TableColumn<>("VariableName");
        TableColumn<Pair<String,Integer>,String> columnValue=new TableColumn<>("Value");

        symbolTableView.getColumns().add(columnVariable);
        symbolTableView.getColumns().add(columnValue);

        setColumnPropertySymTable(columnVariable,columnValue);

        leftPane.getChildren().add(new Label("Execution Stack"));
        leftPane.getChildren().add(exeStackView);
        leftPane.getChildren().add(new Label("Symbol Table"));
        leftPane.getChildren().add(symbolTableView);

    }

    public void setColumnPropertySymTable(TableColumn<Pair<String,Integer>,String> columnVariable,
                                          TableColumn<Pair<String,Integer>,String> columnValue){

        columnVariable.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Integer>, String> param) {
                Pair<String,Integer> pair=param.getValue();
                return new SimpleStringProperty(pair.getFirst());
            }
        });
        columnValue.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getSecond().toString());
            }
        });

    }

    public void createMiddlePane(VBox middlePane){

        outView = new ListView<>(FXCollections.observableArrayList());

        heapView = new TableView<>();
        TableColumn<Pair<Integer,Integer>,String> leftHeapColumn=
                new TableColumn<Pair<Integer,Integer>, String>("Address");
        TableColumn<Pair<Integer,Integer>,String> rightHeapColumn=
                new TableColumn<Pair<Integer,Integer>,String>("Value");

        heapView.getColumns().add(leftHeapColumn);
        heapView.getColumns().add(rightHeapColumn);
        setColumnsPropertyHeap(leftHeapColumn,rightHeapColumn);



        fileTableView = new TableView<>();
        TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String> columnLeft=
                new TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String>("File Descriptor");
        TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String> columnRight=
                new TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String>("Name");

        fileTableView.getColumns().add(columnLeft);
        fileTableView.getColumns().add(columnRight);
        setColumnsPropertyFileTable(columnLeft,columnLeft);



        middlePane.getChildren().add(new Label("Output List"));
        middlePane.getChildren().add(outView);
        middlePane.getChildren().add(new Label("Heap Table"));
        middlePane.getChildren().add(heapView);
        middlePane.getChildren().add(new Label("File Table"));
        middlePane.getChildren().add(fileTableView);
    }

    public void setColumnsPropertyHeap(TableColumn<Pair<Integer,Integer>,String> leftHeapColumn,
                                       TableColumn<Pair<Integer,Integer>,String> rightHeapColumn){
        leftHeapColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<Integer, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getFirst().toString());
            }
        });
        rightHeapColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<Integer, Integer>, String> param) {
                return new SimpleStringProperty(param.getValue().getSecond().toString());
            }
        });
    }

    public void setColumnsPropertyFileTable(TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String> columnLeft,
                                            TableColumn<Pair<Integer,Pair<String,BufferedReader>>,String> columnRight){
        columnLeft.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<Integer, Pair<String, BufferedReader>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<Integer, Pair<String, BufferedReader>>, String> param) {
                return new SimpleStringProperty(param.getValue().getFirst().toString());
            }
        });

        columnRight.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<Integer, Pair<String, BufferedReader>>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<Integer, Pair<String, BufferedReader>>, String> param) {
                return new SimpleStringProperty(param.getValue().getSecond().getFirst());
            }
        });

    }

    public void createRightPane(VBox rightPane){

        runOneStepButton = new Button("Run One Step");

        noProgramsField = new TextField(Integer.toString(programs.size()));

        rightPane.getChildren().add(new Label("Program States"));
        rightPane.getChildren().add(programStatesView);
        rightPane.getChildren().add(runOneStepButton);
        rightPane.getChildren().add(noProgramsField);

    }


    public static void main(String[] args) {
        launch(args);
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


            programStatesView = new ListView<>(FXCollections.observableArrayList(programs));



        }
        catch (Exception e){
            System.out.println("Not cool, you got an error" + e.getMessage());
        }
    }

}
