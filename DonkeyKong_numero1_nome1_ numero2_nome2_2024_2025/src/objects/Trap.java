package objects;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject implements Interactable{
	private boolean isHidden = false;
	private String name = "Trap";
	


	public Trap(Point2D initialPosition) {
		super(initialPosition);
	}

	public void setHidden(){
		isHidden=true;
		setName("Wall");
	}

	public boolean isHidden(){
		return isHidden;
	}

	public void setName(String n){
		name=n;
	}

    @Override
	public String getName() {
		return name;
	}

	

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void interact(Manel manel) {
		if(isHidden()){
			setName("Trap");
		}
		manel.hurt(10);
	}

	@Override
	public boolean isDeletable() {
		return false;
	}

	@Override
	public boolean isInterectable(ImageTile obj) {
		if(obj.getName().equals("JumpMan")){
            return true;
        }//pra ja so isto
        return false;
	}

	

}

 