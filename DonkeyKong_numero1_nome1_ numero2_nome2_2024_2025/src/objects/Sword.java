package objects;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject implements Interactable{
	
    public Sword(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public int getLayer() {
        return 1;
    }
    
    @Override
    public boolean isDeletable(){
		return true;
	}

    @Override
	public void interact(Manel manel) {
		manel.giveSword();
	}

    @Override
    public boolean isInterectable(GameObject obj) {
        if(obj.getName().equals("JumpMan")){
            return true;
        }//pra ja so isto
        return false;
    }
    
}
