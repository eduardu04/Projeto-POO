package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable{
	private int damageLevel=0;
	
	public Manel(Point2D initialPosition){
		super(initialPosition);
	}
	
	
	@Override
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

	public void addDamage(int lev){
		damageLevel+=lev;
	}

	public int getDamage(){
		return damageLevel;
	}
	
	
}
