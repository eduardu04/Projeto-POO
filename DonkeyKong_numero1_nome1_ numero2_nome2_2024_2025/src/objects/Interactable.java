package objects;

import pt.iscte.poo.gui.ImageTile;

public interface Interactable extends ImageTile{   
	void interact(Manel manel);
	boolean isDeletable();
	boolean isInterectable(GameObject obj);
	

}
