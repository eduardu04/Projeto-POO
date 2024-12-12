package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;

public interface Movable extends ImageTile{
    void move(Direction d);
}