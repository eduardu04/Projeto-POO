package objects;

import pt.iscte.poo.utils.Point2D;

public class Bife extends GameObject implements Interactable, Timable{
	private boolean isRotten = false;
	private int ticks = 0;
	private String name = "GoodMeat";
	
	public Bife(Point2D initialPosition){
        super(initialPosition);
    }
	
    @Override
	public String getName() {
		return name;
	}
	
    @Override
	public int getLayer() {
		return 1;
	}
	
	public void setRotten() {
		isRotten = true;
		name = "BadMeat";
	}
	
	@Override
	public void interact(Manel manel) {
		if(isRotten == true) {
			manel.hurtManel(10);
		} else {
			manel.healManel(25);
		}
	}

        @Override
	public boolean isDeletable(){
		return true;
	}

	@Override
	public int checkInnerClock() {
		return ticks;
	}
	
	@Override
	public void processTick()	{
		if(ticks > 10) {
			setRotten();
		}
		ticks++;
	}

}
