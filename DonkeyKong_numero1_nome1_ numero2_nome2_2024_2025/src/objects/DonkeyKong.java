package objects;



import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class DonkeyKong extends GameObject implements Movable, Interactable{
    private int vida = 60;

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

    public void hurtDonkeyKong(int damage){
        vida -= damage;
    }

    @Override
    public boolean isDeletable(){
        return vida <= 0;
    }

    public void interact(Manel manel) {
        manel.hurtManel(35);
        hurtDonkeyKong(manel.getDamage());
    }
    
}
