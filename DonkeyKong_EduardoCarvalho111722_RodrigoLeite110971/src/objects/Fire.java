package objects;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Fire extends GameObject implements Movable{

    public Fire(Point2D initialPosition){
        super(initialPosition);
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    @Override //o fogo só se mexe para baixo
    public void move(Direction d) {
        super.position = super.position.plus(d.asVector());	
    }
    

    
}
