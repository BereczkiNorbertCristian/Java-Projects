package Controller;

import Model.*;
import Repository.ShapeRepository;

/**
 * Created by bnorbert on 09.10.2016.
 */
public class Controller {

    ShapeRepository repo;

    public Controller(ShapeRepository repo){
        this.repo=repo;
    }

    public void add(String shapeId, String volume, String shapeNrType) throws ArrayIndexOutOfBoundsException,NumberFormatException{

        long shapeIdLong,volumeLong,shapeNrTypeLong;

        try {
            shapeIdLong = Long.parseLong(shapeId);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("ShapeId not a valid number\n");
        }
        try{
            volumeLong=Long.parseLong(volume);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Volume not a valid number\n");
        }
        try{
            shapeNrTypeLong=Long.parseLong(shapeNrType);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("Nr type not valid\n");
        }


        Shape shape;

        GeometricShapes shapeType;
        if(shapeNrTypeLong == 1L){
            shapeType=GeometricShapes.CUBE;
        }
        else if(shapeNrTypeLong == 2L){
            shapeType=GeometricShapes.CYLINDER;
        }
        else{
            shapeType=GeometricShapes.SPHERE;
        }

        if(shapeType == GeometricShapes.CUBE){
            shape=new Cube(shapeIdLong,volumeLong);
        }
        else {
            if (shapeType == GeometricShapes.SPHERE) {
                shape = new Sphere(shapeIdLong, volumeLong);
            }
            else {
                shape = new Cylinder(shapeIdLong, volumeLong);
            }
        }
        repo.add(shape);
    }

    public void remove(String shapeId) throws ArrayIndexOutOfBoundsException,NumberFormatException{

        long shapeIdLong;
        try{
            shapeIdLong=Long.parseLong(shapeId);
        }
        catch (NumberFormatException e){
            throw new NumberFormatException("ShapeId not a number!\n");
        }

        repo.remove(shapeIdLong);

    }

    public String getAbove25Volume(){
        return repo.getAbove25Volume();
    }



}
