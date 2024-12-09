package objects;



import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject implements Movable{

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

    @Override
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}
    
}
