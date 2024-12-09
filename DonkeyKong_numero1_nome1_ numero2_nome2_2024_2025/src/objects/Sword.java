package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable{
    private boolean shouldDisappear=false;
	
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
<<<<<<< Updated upstream
		ImageGUI.getInstance().setStatusMessage("Espada apanhada!" + " Dano: " + manel.getDamage());
=======
        shouldDisappear=true;
        
>>>>>>> Stashed changes
	}

    @Override
    public boolean shouldDisappear() {
        return shouldDisappear;
    }

    
}
