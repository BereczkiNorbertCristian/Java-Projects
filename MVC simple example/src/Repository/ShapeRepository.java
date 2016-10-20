package Repository;

import Model.Shape;

/**
 * Created by bnorbert on 07.10.2016.
 */
public interface ShapeRepository {

    public void add(Shape shape) throws ArrayIndexOutOfBoundsException;

    public void remove(long shapeId) throws ArrayIndexOutOfBoundsException;

    public String getAbove25Volume();

}
