package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Princesa extends GameObject implements Interactable    {
    private boolean interactable=true;
    private boolean isSaved = false;

    public Princesa(Point2D initialPosition) {
        super(initialPosition);
    }

    @Override
    public void interact(Manel manel) {
        isSaved = true;
    }

    @Override
    public boolean isDeletable() {
        return false;
    }

    @Override
    public String getName() {
        return "Princess";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public boolean isSaved(){
        return isSaved;
    }

    
    @Override
	public boolean isInteractable() {
		return interactable;
	}

	@Override
	public void notInteractable() {
		//tem de ser sempre
	}


}
