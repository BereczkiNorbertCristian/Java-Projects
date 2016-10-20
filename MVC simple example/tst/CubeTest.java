import Model.Cube;
import Model.Shape;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by bnorbert on 07.10.2016.
 */
public class CubeTest {

    @Test
    public void cubeTest(){

        Shape cube=new Cube(12,34);
        Shape cube1=new Cube(132,11);



        ArrayList<Shape> shapes=new ArrayList<>();
        shapes.add(cube);
        shapes.add(cube1);

        Assert.assertEquals(cube.isAboveVolume25(),true);
        Assert.assertEquals(cube1.isAboveVolume25(),false);

        for(Shape shape : shapes){
            if(shape.getVolume() > 0){
                Assert.assertEquals(true,true);
            }
        }

    }

}
