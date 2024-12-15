package objects;



import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Bat extends GameObject implements Movable, Interactable{
    private boolean interactable = true;
    private int damage = 80;

    public Bat(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "Bat";
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

        return true;
    }

    public void interact(Manel manel) {
        if(isInteractable()){
            manel.hurt(damage);
        }
        
        
        

    }

   

    public int getDamage() {
        return damage;
    }


    @Override
	public boolean isInteractable() {
		return interactable;
	}

	@Override
	public void notInteractable() {
		interactable=false;
	}
}
