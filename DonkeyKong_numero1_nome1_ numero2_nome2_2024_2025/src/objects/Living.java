package objects;

import pt.iscte.poo.gui.ImageTile;

public interface Living extends ImageTile{
    int getHealth();
    void hurt(int damage);
    
}
