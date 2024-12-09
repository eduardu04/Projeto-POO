package objects;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable, Living{
	private int damageLevel = 0;
	private int lives = 3;
	private int health = 100;
	
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
	
	
	@Override
	public int getDamage(){
		return damageLevel;
	}
	@Override
	public int getHealth() {
		return health;
	}

	public int getLives() {
		return lives;
	}

	public void giveSword() {
		damageLevel += 15;
	}

	@Override
	public void hurt(int damage) {
		health -= damage;
	}

	
	public void heal(int heal) {
		health += heal;
	}

}
