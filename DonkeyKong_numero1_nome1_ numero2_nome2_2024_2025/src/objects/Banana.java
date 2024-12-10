package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends GameObject implements Interactable, Movable{
	
	public Banana(Point2D initialPosition){
        super(initialPosition);
    }
	
    @Override
	public String getName() {
		return "Banana";
	}
	
    @Override
	public int getLayer() {
		return 2;
	}

	
	@Override
	public void interact(Manel manel) {
		manel.hurtManel(10);
	}

    @Override
	public boolean isDeletable(){
		return true;
	}

	@Override
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}
	
}
