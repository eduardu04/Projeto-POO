package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile{
    protected Point2D position;

    public GameObject(Point2D point){
		position = point;
	}

    public abstract String getName();
  
    public Point2D getPosition(){
        return position;
    }

    public abstract int getLayer();

}
