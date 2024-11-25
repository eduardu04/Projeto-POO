package objects;

import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject{

    public Sword(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
}
