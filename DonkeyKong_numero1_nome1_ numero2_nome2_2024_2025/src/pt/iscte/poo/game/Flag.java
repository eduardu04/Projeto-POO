package objects;

import pt.iscte.poo.game.Room;
import pt.iscte.poo.utils.Point2D;

public class Flag extends GameObject implements Interactable {

	public Flag(Point2D startingPosition) {
		super(startingPosition);
	}
	
	@Override
	public void interact(Manel manel) {
		Room.teleportManel(new Point2D(0,0));
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public String getName() {
		return "Flag";
	}

	@Override
	public int getLayer() {
		return 1;
	}

}
