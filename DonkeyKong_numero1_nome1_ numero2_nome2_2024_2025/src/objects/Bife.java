package objects;

import pt.iscte.poo.utils.Point2D;

public class Bife extends GameObject implements Interactable, Timable{
	private boolean isRotten = false;
	private int ticks = 0;
	private String name = "GoodMeat";
	private boolean shouldDisappear=false;
	
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
			manel.hurt(10);
		} else {
			manel.healManel(25);
		}
		shouldDisappear=true;
	}

	@Override
	public int checkInnerClock() {
		return ticks;
	}
	
	@Override
	public void processTick()	{
		if(ticks > 20) {
			setRotten();
		}
		ticks++;
	}

	@Override
	public boolean shouldDisappear() {
		return shouldDisappear;
	}

	

	
}
