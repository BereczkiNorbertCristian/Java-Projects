package Model;

/**
 * Created by bnorbert on 07.10.2016.
 */
public abstract class Shape {
    protected long volume;
    protected long shapeId;
    protected static final String measure = "cm3";

    public abstract void setShapeId(long shapeId);

    public abstract long getShapeId();

    public abstract void setVolume(long volume);

    public abstract long getVolume();

    public abstract boolean isAboveVolume25();


}
