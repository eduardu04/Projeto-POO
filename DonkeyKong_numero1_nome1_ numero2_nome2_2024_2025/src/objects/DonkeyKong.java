package objects;



import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject{

    public DonkeyKong(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }
    
}
