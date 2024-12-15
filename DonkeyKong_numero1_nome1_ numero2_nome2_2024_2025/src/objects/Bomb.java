package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject implements Interactable, Timable{
    private boolean interactable=true;
    private boolean largada=false;
    private boolean apanhada=false;
    private int ticks = 0;
    private boolean exploded=false;

    public Bomb(Point2D initialPosition){
        super(initialPosition);
    }
	
    @Override
	public String getName() {
		return "Bomb";
	}
	
    @Override
	public int getLayer() {
		return 1;
	}

    public void apanhar() {
		apanhada=true;
        System.out.println("Bomba apanhada");
	}
    public boolean isApanhada() {
		return apanhada;
	}
	
	public void largar() {
		largada=true;
        notInteractable();
        System.out.println("Bomba largada");
	}

    public boolean isLargada(){
        return largada;
    }
    
    


	
	@Override
	public void interact(Manel manel) {
        if(isInteractable()){
            if(!isApanhada()&& !isLargada()) {
                manel.getsBomb(this);
                apanhar();
            }
        }
		
	}


    @Override
	public int checkInnerClock() {
		return ticks;
	}
	
	@Override
	public void processTick()	{
        
		if(isLargada()) {
            System.out.println("Ticks bomba: "+ checkInnerClock());
            if(ticks==30){
                explode();
            }
            ticks++;
		}
		
	}

    
    public boolean hasExploded(){
        return exploded;
    }

    @Override
    public boolean isDeletable() {
        if(isLargada() && hasExploded()){
            return true;
        }
        if(isApanhada()){
            return true;
        }
        if(hasExploded()){
            return true;
        }
        return false;
    }

    public void explode(){
        exploded=true;
    }


    

    public void setPosition(Point2D p){
        super.position = p;	
    }

    

    @Override
    public boolean hasChanged() {
        return hasExploded();
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
