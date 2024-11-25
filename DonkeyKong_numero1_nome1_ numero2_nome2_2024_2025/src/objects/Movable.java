package objects;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.gui.ImageTile;

public interface Movable extends ImageTile{
    void move(Direction d);
    
}
