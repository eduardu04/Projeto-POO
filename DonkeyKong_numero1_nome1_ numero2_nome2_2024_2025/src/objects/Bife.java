package objects;

import pt.iscte.poo.utils.Point2D;

public class Bife extends GameObject implements Interactable, Timable{
	private boolean isRotten = false;

	public Bife(Point2D initialPosition){
        super(initialPosition);
    }
	
	public String getName() {
		return "Bife";
	}

	public int getLayer() {
		return 1;
	}
	
	public void setRotten() {
		isRotten = true;
	}
	
	public void interact(Manel manel) {
		if(isRotten == true) {
			manel.hurtManel(10);
		} else {
			manel.healManel(25);
		}
	}


	public int checkObjectTick() {
		return 0;
	}
}
