package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Floor extends GameObject{

	public Floor(Point2D initialPosition) {
		super(initialPosition);
	}

	@Override
	public String getName() {
		return "Floor";
	}




	@Override
	public int getLayer() {
		return 0;
	}




}
