package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject{

	public Trap(Point2D initialPosition) {
		super(initialPosition);
	}

    @Override
	public String getName() {
		return "Trap";
	}




	@Override
	public int getLayer() {
		return 1;
	}

	
    
}
