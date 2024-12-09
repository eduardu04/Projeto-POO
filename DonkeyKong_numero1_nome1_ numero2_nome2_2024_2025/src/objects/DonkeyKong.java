package objects;



import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject implements Movable, Interactable,Living{
    private int health = 100;
    private int damage = 20;
    private boolean shouldDisappear = false;

    public DonkeyKong(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override
	public void move(Direction d) {
		super.position = super.position.plus(d.asVector());	
	}

    @Override
    public void interact(Manel manel) {
        manel.hurt(getDamage());
        this.hurt(manel.getDamage());
        if(getHealth()<=0){
            shouldDisappear=true;
        }
        
    }


    @Override
    public int getHealth() {
        return health;
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public void hurt(int damage) {
        health -= damage;
    }

    @Override
    public boolean shouldDisappear() {
        return shouldDisappear;
    }

    
    
}
