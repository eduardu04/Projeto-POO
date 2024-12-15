package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Banana extends GameObject implements Interactable, Movable{
	private boolean interactable = true;
	
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
		if(isInteractable()){
			manel.hurt(10);
		}
		
	}

    @Override
	public boolean isDeletable(){
		return true;
	}

	@Override
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}


	

	@Override
	public boolean isInteractable() {
		return interactable;
	}

	@Override
	public void notInteractable() {
		interactable=false;
	}
	
}
