package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap implements ImageTile{
    private Point2D position;

	public Trap(Point2D initialPosition) {
		position=initialPosition;
	}

	@Override
	public String getName() {
		return "Trap";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}
    
}
