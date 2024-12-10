package pt.iscte.poo.game;

import java.io.File;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;

public class GameEngine implements Observer {

	
	private Room currentRoom = Room.readRoomFile(new File("rooms/room0.txt"));
	private int lastTickProcessed = 0;
	
	public GameEngine() {
		ImageGUI.getInstance().update();
	}

	@Override
	public void update(Observed source) {

		if (ImageGUI.getInstance().wasKeyPressed()) {
			int k = ImageGUI.getInstance().keyPressed();
			System.out.println("Keypressed " + k);
			if (Direction.isDirection(k)) {
				System.out.println("Direction! ");
				currentRoom.moveManel(Direction.directionFor(k));
			}
		}
		int t = ImageGUI.getInstance().getTicks();
		while (lastTickProcessed < t) {
			processTick();
		}

		currentRoom.processTimables();
		currentRoom.interact();

		if(lastTickProcessed % 3 == 0)	{
			currentRoom.attack();
		}

		currentRoom.manelFall();
		currentRoom.manelStatus();		
		ImageGUI.getInstance().update();

	}

	private void processTick() {
		if(lastTickProcessed%2==0){
			currentRoom.moveMovables();
		}
		System.out.println("Tic Tac : " + lastTickProcessed);
		lastTickProcessed++;
	}

	



}
