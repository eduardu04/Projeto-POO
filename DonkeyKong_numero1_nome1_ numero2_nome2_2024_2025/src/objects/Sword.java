package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable{
	
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
    
	public void interact(Manel manel) {
		manel.giveSword();
	}
    
}
