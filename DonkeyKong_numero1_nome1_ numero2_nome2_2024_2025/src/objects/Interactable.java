package objects;

import pt.iscte.poo.gui.ImageTile;

public interface Interactable extends ImageTile{  
	void interact(Manel manel);
	boolean isDeletable();
	void notInteractable();
	boolean isInteractable();
	

}
