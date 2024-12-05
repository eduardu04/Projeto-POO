package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable{
	private int damageLevel = 0;
	private int lives = 3;
	private int health = 100;
	
	public Manel(Point2D initialPosition){
		super(initialPosition);
	}
	
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}

	public String getName() {
		return "JumpMan";
	}

	public int getLayer() {
		return 2;
	}
	
	public void hurtManel(int damage) {
		health -= damage;
	}
	
	public void healManel(int heal) {
		health += heal;
	}

	public int getDamage(){
		return damageLevel;
	}
	
	public int getHealth() {
		return health;
	}

	
	public void giveSword() {
		damageLevel += 15;
	}

	public int getLives() {
		return lives;
	}
}
