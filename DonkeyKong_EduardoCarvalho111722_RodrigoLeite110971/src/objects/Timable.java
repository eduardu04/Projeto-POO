package objects;
import pt.iscte.poo.gui.ImageTile;

public interface Timable extends ImageTile{
	int checkInnerClock();
	void processTick();
	boolean hasChanged();
}