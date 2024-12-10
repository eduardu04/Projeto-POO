package objects;



import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject implements Movable, Interactable, Living{
    private int health = 60;
    private int damage = 35;

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
    public boolean isDeletable(){
        return health <= 0;
    }

    public void interact(Manel manel) {
        hurtDonkeyKong(manel.getDamage());
    }
}
