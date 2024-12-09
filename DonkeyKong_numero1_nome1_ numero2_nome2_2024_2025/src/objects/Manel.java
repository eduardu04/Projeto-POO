package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable,Living{
	private int damageLevel = 0;
<<<<<<< Updated upstream
=======
	private int lives = 1;
	private int health = 100;
	private boolean manelRIP=false;
>>>>>>> Stashed changes
	
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
	
	

	public int getDamage(){
		return damageLevel;
	}
<<<<<<< Updated upstream
=======
	@Override
	public int getHealth() {
		return health;
	}


	public int getLives() {
		return lives;
	}
>>>>>>> Stashed changes

	public void giveSword() {
		damageLevel += 15;
		
	}
<<<<<<< Updated upstream
	
	
=======

	@Override
	public void hurt(int damage) {
		if(lives<=1 && health-damage<=0){
			manelRIP=true;
			health = 0;
			lives=0;
			return;
		} 
		if(health-damage<=0){
			lives--;
			health = 100+(health-damage);
		}
		else{
			health-=damage;
		}
		
	}

	public boolean rip(){
		return manelRIP;
	}

>>>>>>> Stashed changes
}
