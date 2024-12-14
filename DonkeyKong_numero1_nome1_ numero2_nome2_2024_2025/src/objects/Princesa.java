package objects;

import pt.iscte.poo.utils.Point2D;

public class Princesa extends GameObject implements Interactable    {
    private boolean isSaved = false;

    public Princesa(Point2D initialPosition) {
        super(initialPosition);
    }

    @Override
    public void interact(Manel manel) {
        isSaved = true;
    }

    @Override
    public boolean isDeletable() {
        return false;
    }

    @Override
    public String getName() {
        return "Princess";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public boolean getIsSaved(){
        return isSaved;
    }

    @Override
    public boolean isInterectable(GameObject obj) {
        if(obj.getName().equals("JumpMan")){
            return true;
        }//pra ja so isto
        return false;
    }    


}
