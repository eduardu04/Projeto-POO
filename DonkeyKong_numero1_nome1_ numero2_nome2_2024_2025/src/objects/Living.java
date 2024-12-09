package objects;

import pt.iscte.poo.gui.ImageTile;

public interface Living extends ImageTile{
    int getHealth();
    int getDamage();
    void hurt(int damage);
    void heal(int heal);
}
