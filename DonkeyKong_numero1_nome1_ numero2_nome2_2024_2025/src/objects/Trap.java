package objects;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject implements Interactable{

	public Trap(Point2D initialPosition) {
		super(initialPosition);
	}

    @Override
	public String getName() {
		return "Trap";
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void interact(Manel manel) {
		manel.hurtManel(10);
	}

	@Override
	public boolean isDeletable() {
		return false;
	}
}
