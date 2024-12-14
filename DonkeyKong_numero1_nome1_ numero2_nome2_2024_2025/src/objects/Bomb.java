package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bomb extends GameObject implements Interactable, Timable{
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
        System.out.println("Bomba largada");
	}

    public boolean isLargada(){
        return largada;
    }
    
    


	
	@Override
	public void interact(Manel manel) {
		if(!isApanhada()) {
			manel.getsBomb(this);
            apanhar();
		} 
	}

    public void interactWithOthers(Living m) {
		if(isLargada()) {
			explode();
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
    public boolean isInterectable(GameObject obj) {
        if(!isLargada() && obj.getName().equals("JumpMan")){
            return true;
        }//pra ja so isto
        return false;
    }

    @Override
    public boolean hasChanged() {
        return hasExploded();
    }
}
