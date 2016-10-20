package Repository;

import Model.Shape;

/**
 * Created by bnorbert on 08.10.2016.
 */
public class InMemoryShapeRepository implements ShapeRepository {

    public Shape storage[];
    public int currIdx;
    public Shape filteredShapes[];
    public int filteredIdx;

    public InMemoryShapeRepository(){
        storage=new Shape[1000];
        currIdx=0;
        filteredShapes=new Shape[1000];
        filteredIdx=0;
    }
    @Override
    public void add(Shape shape) throws ArrayIndexOutOfBoundsException{

        if(currIdx == storage.length){
            throw new ArrayIndexOutOfBoundsException("Memory full, remove an element to add another");
        }
        storage[currIdx++]=shape;
    }
    @Override
    public void remove(long shapeId) throws ArrayIndexOutOfBoundsException{

        if(currIdx == 0) {
            throw new ArrayIndexOutOfBoundsException("There are no elements in the array");
        }

        for(int i=0;i<currIdx;i++){
            if(storage[i].getShapeId() == shapeId){
                for(int j=i+1;j<currIdx;j++){
                    storage[j-1]=storage[j];
                }
                currIdx--;
                break;
            }
        }
    }

    private int filteredShapesSize(){
        return filteredIdx;
    }

    private Shape[] filter(){

        filteredIdx=0;

        for(int i=0;i<currIdx;i++){
            if(storage[i].isAboveVolume25()){
                filteredShapes[filteredIdx++]=storage[i];
            }
        }

        return filteredShapes;
    }

    @Override
    public String getAbove25Volume(){

        Shape shapesFiltered[]=filter();
        String string="";
        for(int i=0;i<filteredIdx;i++){
            string+=filteredShapes[i].toString();
        }
        return string;
    }

    @Override
    public String toString(){

        String string="";
        for(int i=0;i<currIdx;i++){
            string+=storage[i].toString();
        }
        return string;
    }


}
