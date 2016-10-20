package Model;

/**
 * Created by bnorbert on 07.10.2016.
 */
public class Cylinder extends Shape {

    public Cylinder(long shapeId,long volume) {
        this.volume = volume;
        this.shapeId=shapeId;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getVolume() {
        return volume;
    }

    @Override
    public long getShapeId(){
        return shapeId;
    }

    @Override
    public void setShapeId(long shapeId){
        this.shapeId=shapeId;
    }

    public boolean isAboveVolume25() {

        if (this.volume > 25L) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public String toString(){

        String string="CYLINDER{ Id:" + Long.toString(shapeId) +" Volume" +Long.toString(volume) + "} ";
        return string;
    }
}
