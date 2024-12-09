package objects;

import pt.iscte.poo.utils.Point2D;

public class Bife extends GameObject implements Interactable, Timable{
	private boolean isRotten = false;
	private int ticks = 0;
	private String name = "GoodMeat";
	
	public Bife(Point2D initialPosition){
        super(initialPosition);
    }
	
	public String getName() {
		return name;
	}
	
	public int getLayer() {
		return 1;
	}
	
	public void setRotten() {
		isRotten = true;
		name = "BadMeat";
	}
	
	public void interact(Manel manel) {
		if(isRotten == true) {
			manel.hurtManel(10);
		} else {
			manel.healManel(25);
		}
	}

	public int checkInnerClock() {
		return ticks;
	}
	
	public void processTick()	{
		if(ticks > 6) {
			setRotten();
		}
		
		ticks++;
	}
}
