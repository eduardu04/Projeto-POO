package objects;
import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject implements  Interactable{
    private int doorStatus = -1;
    
    public Door(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        if(doorStatus == -1){
            return "DoorClosed";
        }
        return "DoorOpen";
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void openDoor(){
        doorStatus = 0;
    }

    public boolean isClosed() {
        return doorStatus == -1;
    }

    @Override
    public void interact(Manel manel) {
       openDoor();
    }

    @Override
    public boolean isDeletable() {
        return false;
    }

    public int getDoorStatus() {
        return doorStatus;
    }
}
