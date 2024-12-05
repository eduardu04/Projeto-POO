package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;

public interface Timable extends ImageTile{
	int checkObjectTick();
}