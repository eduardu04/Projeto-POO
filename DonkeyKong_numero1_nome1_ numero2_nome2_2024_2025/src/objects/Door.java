package objects;

import pt.iscte.poo.utils.Point2D;

public class Door extends GameObject{
    private boolean closedDoor;

    public Door(Point2D initialPosition){
        super(initialPosition);
        closedDoor=true;
    }

    @Override
    public String getName() {
        if(closedDoor){
            return "DoorClosed";
        }
        return "DoorOpen";
        
    }

    @Override
    public int getLayer() {
        return 1;
    }

    public void openDoor(){
        closedDoor=false;
    }
    
}
