package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject{
	
	public Manel(Point2D initialPosition){
		super(initialPosition);
	}
	
	
//Alterei e pus parametro Direction
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}


	@Override
	public String getName() {
		return "JumpMan";
	}




	@Override
	public int getLayer() {
		return 2;
	}
	
	
}
