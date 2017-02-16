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
import Model.ProcMapping;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.BufferedReader;
import java.util.*;

public class Main extends Application {

    HBox root;
    ListView<String> exeStackView,outView,symTableStackView;
    TableView<Pair<String,Integer>> symbolTableView;
    TableView<Pair<Integer,Integer>> heapView;
    TableView<Pair<String, ProcMapping>> procTableView;
    TableView<Pair<Integer,Pair<String,BufferedReader>>> fileTableView;
    ListView<EntryIdentifier> programStatesView;
    TextField noProgramsField;
    Button runOneStepButton;
    ProgramController programController;



    @Override
    public void start(Stage primaryStage) throws Exception{

        //Main entry of getting ready

        initGUI(primaryStage);
        listenOnSelection();
        readyButton();

        Stage secondaryStage = new Stage();
        SecondaryController secondaryController=new SecondaryController(secondaryStage);
        readyOnHiddingSecondary(primaryStage,secondaryStage,secondaryController);
        secondaryController.startSecondaryStage();

    }

    private void populateIdentifierList(){

        List<ProgramState> programStates = programController.getProgramStates();
        List<EntryIdentifier> entryIdentifiers=new ArrayList<>();
        for(ProgramState program : programStates){

            entryIdentifiers.add(new EntryIdentifier(program));
        }
        programStatesView.setItems(FXCollections.observableArrayList(entryIdentifiers));

    }

    private void readyOnHiddingSecondary(Stage primaryStage,Stage secondaryStage,SecondaryController secondaryController){


        secondaryStage.setOnHiding(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                programController=secondaryController.getProgramController();

                populateIdentifierList();
                primaryStage.show();

            }
        });
    }

    public void readyButton(){

        runOneStepButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                programStatesView.getSelectionModel().clearSelection();
                clearDataInWindow();
                programController.allStepGUI();
                populateIdentifierList();
                programStatesView.getSelectionModel().select(0);
                noProgramsField.setText(Integer.toString(programController.getProgramStates().size()));
            }
        });
    }

    private void clearDataInWindow(){
        exeStackView.setItems(FXCollections.observableArrayList());
        symbolTableView.setItems(FXCollections.observableArrayList());
        outView.setItems(FXCollections.observableArrayList());
        heapView.setItems(FXCollections.observableArrayList());
        fileTableView.setItems(FXCollections.observableArrayList());
    }

    private void listenOnSelection(){

        programStatesView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<EntryIdentifier>() {
                    @Override
                    public void changed(ObservableValue<? extends EntryIdentifier> observable, EntryIdentifier oldValue, EntryIdentifier newValue) {

                        if(newValue == null){
                            return;
                        }
                        exeStackView.setItems(getExeStackOList(newValue));
                        symbolTableView.setItems(getSymbolTableOList(newValue));
                        outView.setItems(getOutOList(newValue));
                        heapView.setItems(getHeapOList(newValue));
                        fileTableView.setItems(getFileTableOList(newValue));
                        procTableView.setItems(getProcTableOList(newValue));
                        symTableStackView.setItems(getSymbolTableStack(newValue));
                    }
                }
        );

    }


    private ObservableList<Pair<String,ProcMapping>> getProcTableOList(EntryIdentifier entryIdentifier){
        ProgramState state=entryIdentifier.getProgram();
        List<Pair<String,ProcMapping>> ret=new ArrayList<>();
        if(state.getAllSymTables() == null) return FXCollections.observableArrayList();
        Iterator<Map.Entry<String,ProcMapping>> iter=state.getProcTable().iterator();
        while (iter.hasNext()){
            Map.Entry<String,ProcMapping> entry=iter.next();
            ret.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<Pair<Integer,Integer>> getHeapOList(EntryIdentifier entryIdentifier){

        ProgramState state=entryIdentifier.getProgram();
        List<Pair<Integer,Integer>> ret=new ArrayList<>();
        Iterator<Map.Entry<Integer,Integer>> iter=state.getHeap().iterator();
        while (iter.hasNext()){
            Map.Entry<Integer,Integer> entry=iter.next();
            ret.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<Pair<Integer,Pair<String,BufferedReader>>> getFileTableOList(EntryIdentifier entryIdentifier){

        ProgramState state=entryIdentifier.getProgram();
        List<Pair<Integer,Pair<String,BufferedReader>>> ret=new ArrayList<>();
        Iterator<Map.Entry<Integer,Pair<String,BufferedReader>>> iter=state.getFileTable().iterator();
        while(iter.hasNext()){
            Map.Entry<Integer,Pair<String,BufferedReader>> entry=iter.next();
            ret.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return FXCollections.observableArrayList(ret);

    }

    public ObservableList<String> getOutOList(EntryIdentifier entryIdentifier){
        ProgramState state=entryIdentifier.getProgram();
        List<String> ret=new ArrayList<>();
        Iterator<String> iter=state.getOut().iterator();
        while (iter.hasNext()){
            ret.add(iter.next());
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<Pair<String,Integer>> getSymbolTableOList(EntryIdentifier entryIdentifier){
        ProgramState state=entryIdentifier.getProgram();
        List<Pair<String,Integer>> ret=new ArrayList<>();
        Iterator<Map.Entry<String,Integer>> iter = state.getSymbolTable().iterator();
        while(iter.hasNext()){
            Map.Entry<String,Integer> mapEntry=iter.next();
            Pair<String,Integer> pr=new Pair<>(mapEntry.getKey(),mapEntry.getValue());
            ret.add(pr);
        }
        return FXCollections.observableArrayList(ret);
    }

    public ObservableList<String> getExeStackOList(EntryIdentifier entryIdentifier){

        if(entryIdentifier == null){
            System.out.println("ID");
        }
        ProgramState state=entryIdentifier.getProgram();
        List<String> ret=new ArrayList<>();
        Iterator<IStatement> iter = state.getExecutionStack().iterator();
        while(iter.hasNext()){
            IStatement stmt=iter.next();
            ret.add(stmt.toString());
        }
        return FXCollections.observableArrayList(ret);
    }

    private ObservableList<String> getSymbolTableStack(EntryIdentifier entryIdentifier){
        ProgramState state=entryIdentifier.getProgram();
        List<String> ret=new ArrayList<>();
        if(state.getAllSymTables()==null) return FXCollections.observableArrayList();
        Iterator<IToyMap<String,Integer>> iter=state.getAllSymTables().iterator();
        while (iter.hasNext()){
            IToyMap<String,Integer> symTable=iter.next();
            ret.add(symTable.toString());
        }
        return FXCollections.observableArrayList(ret);
    }



    private void initGUI(Stage primaryStage)throws Exception{

        root = new HBox(30);
        root.setPadding(new Insets(30));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1150, 700));

        VBox leftPane,middlePane,rightPane,rightPlusPane;

        leftPane=new VBox(10);
        middlePane=new VBox(10);
        rightPane=new VBox(10);
        rightPlusPane=new VBox(10);

        createLeftPane(leftPane);
        createMiddlePane(middlePane);
        createRightPane(rightPane);
        createRightPlusPane(rightPlusPane);

        root.getChildren().add(leftPane);
        root.getChildren().add(middlePane);
        root.getChildren().add(rightPane);
        root.getChildren().add(rightPlusPane);

    }

    public void createRightPlusPane(VBox rightPlusPane){

        symTableStackView=new ListView<>(FXCollections.observableArrayList());
        procTableView = new TableView<>();

        TableColumn<Pair<String,ProcMapping>,String> leftColl=new TableColumn<>("Procedure");
        TableColumn<Pair<String,ProcMapping>,String> rightColl=new TableColumn<>("Body and params");

        procTableView.getColumns().add(leftColl);
        procTableView.getColumns().add(rightColl);

        setColumnPropertyProcTable(leftColl,rightColl);

        rightPlusPane.getChildren().add(new Label("Procedures"));
        rightPlusPane.getChildren().add(procTableView);
        rightPlusPane.getChildren().add(new Label("Symbol Table Stack"));
        rightPlusPane.getChildren().add(symTableStackView);

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

    public void setColumnPropertyProcTable(TableColumn<Pair<String,ProcMapping>,String> columnLeft,
                                           TableColumn<Pair<String,ProcMapping>,String> columnRight){
        columnLeft.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, ProcMapping>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, ProcMapping>, String> param) {
                Pair<String,ProcMapping> pair=param.getValue();
                return new SimpleStringProperty(pair.getFirst());
            }
        });
        columnRight.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pair<String, ProcMapping>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, ProcMapping>, String> param) {
                Pair<String,ProcMapping> pair=param.getValue();
                return new SimpleStringProperty(pair.getSecond().toString());
            }
        });
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
        setColumnsPropertyFileTable(columnLeft,columnRight);



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

        noProgramsField = new TextField(Integer.toString(1));

        programStatesView = new ListView<>(FXCollections.observableArrayList());

        rightPane.getChildren().add(new Label("Program States"));
        rightPane.getChildren().add(programStatesView);
        rightPane.getChildren().add(runOneStepButton);
        rightPane.getChildren().add(noProgramsField);

    }




    public static void main(String[] args) {
        launch(args);
    }



}
