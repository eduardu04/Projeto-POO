package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Bife extends GameObject implements Interactable, Timable{
	private boolean interactable=true;
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
		if(isInteractable()){
			if(isRotten == true) {
				manel.hurt(10);
			} else {
				manel.heal(25);
			}
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


	@Override
	public boolean hasChanged() {
		return isRotten;
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
