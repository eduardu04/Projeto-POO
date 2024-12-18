package objects;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Manel extends GameObject implements Movable, Living{
	private int damageLevel = 25;
	private int lives = 3;
	private int health = 100;
	
	public Manel(Point2D initialPosition)	{
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
	public void hurt(int damage) {
		health -= damage;
	}
	
	public void heal(int heal) {
		health += heal;
	}
	@Override
	public int getDamage(){
		return damageLevel;
	}

	public void setDamageLevel(int damage){
		this.damageLevel=damage;
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

	public void setLives(int lives){
		this.lives = lives;
	}

	public void setHealth(int health){
		this.health = health;
	}
}
