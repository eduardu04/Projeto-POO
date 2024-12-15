package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable{
    private boolean interactable=true;
	
    public Sword(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public boolean isDeletable(){
		return true;
	}

    @Override
	public void interact(Manel manel) {
        if(isInteractable()){
            manel.giveSword();
        }
		
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
