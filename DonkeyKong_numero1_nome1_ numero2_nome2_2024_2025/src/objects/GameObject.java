package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class GameObject implements ImageTile {
    protected Point2D position;
    private String name;
    private int layer;

    public GameObject(Point2D initialPosition,String name, int layer){
		position = initialPosition;
        this.name=name;
        this.layer=layer;
	}

    @Override
    public String getName(){
        return name;
    }

    @Override
    public Point2D getPosition() {
        return position;
    }

    @Override
    public int getLayer(){
        return layer;
    }

    
    
}
