package View;

import Controller.Controller;
import Model.GeometricShapes;

import java.util.Scanner;

/**
 * Created by bnorbert on 09.10.2016.
 */
public class Ui {

    Controller controller;
    Scanner scanner=new Scanner(System.in);

    public Ui(Controller controller){
        this.controller=controller;
    }

    public void run(){


        int option=-1;


        while(option != 0){

            this.showMenu();
            option=scanner.nextInt();
            switch (option){
                case 1: this.add();
                    break;
                case 2: this.remove();
                    break;
                case 3: this.showFiltered();
                    break;
                case 0: break;
            }


        }
    }

    private void add(){

        System.out.println("Get shapeId:");
        String shapeId=scanner.next();
        System.out.println("Get volume:");
        String volume=scanner.next();
        System.out.println("Choose shape: 1.CUBE 2.CYLINDER 3.SPHERE");
        String shapeNr=scanner.next();

        try {
            controller.add(shapeId, volume,shapeNr);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    private void remove(){

        System.out.println("Introduce shapeId to be removed:");
        String shapeId=scanner.next();
        try{
            controller.remove(shapeId);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void showFiltered(){

        String filteredShapes=controller.getAbove25Volume();
        System.out.println("These are the shapes with the volume above 25:\n"+filteredShapes);

    }

    private static void showMenu(){

        System.out.println(
                "1.Add a shape\n"
                +"2.Remove a shape\n"
                +"3.Show filtered shapes\n"
                +"0.Exit application\n"
                +"Insert your option now:"
        );

    }



}
