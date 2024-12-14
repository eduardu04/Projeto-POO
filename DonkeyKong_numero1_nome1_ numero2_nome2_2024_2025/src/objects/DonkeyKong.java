package objects;



import pt.iscte.poo.gui.ImageGUI;
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
        if(getHealth()<=manel.getDamage()){
            hurt(manel.getDamage());
            ImageGUI.getInstance().setStatusMessage("Donkey Kong was Killed!");
        }else{
            hurt(manel.getDamage());
            ImageGUI.getInstance().setStatusMessage("Donkey Kong was atacked! Life: "+ getHealth()+"/100");
        }
        
        

    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public void hurt(int damage) {
        health-= damage;
    }

    @Override
    public boolean isInterectable(GameObject obj) {
        if(obj.getName().equals("JumpMan")){
            return true;
        }//pra ja so isto
        return false;
    }
}
