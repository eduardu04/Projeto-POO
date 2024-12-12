package pt.iscte.poo.game;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Vector2D;
public class Room {
	
	private static Point2D heroStartingPosition;
	private static Point2D doorPosition;
	private static Manel manel;
	private static String level;
	private static char[][] matrixRoom;
	private static List<Interactable> objetosInteractable;
	private static List<Movable> objetosMoveis;
	private static List<Timable> objetosTimable;
	private static List<GameObject> objetosFixos;
	private static Door currentDoor;
	private int lastTickProcessedRoom;
	private boolean loadNextLevel = false;
	private static int levelNum;
				
		public Room(int levelNum) {
			this.levelNum = levelNum;
			level = "room" + levelNum + ".txt";
	
			if(levelNum > 0){
				clearPreviousLevel();
				respawnManel(heroStartingPosition, false);
			}
	
			addFloor();
			objetosInteractable = new ArrayList<>();
			objetosMoveis = new ArrayList<>();
			objetosTimable = new ArrayList<>();
			objetosFixos = new ArrayList<>();
			
			System.out.println("A carregar nível: " + level);
			readRoomFile(level);
		}
			
		public void processRoom(){
			manelFall();
			manelStatus();
	
			if(lastTickProcessedRoom % 3 == 0){
				moveMovables();
			}
			if(lastTickProcessedRoom % 8 == 0)	{
				attack();
			}
			
			interact();
			processTimables();
			
			if(currentDoor.getDoorStatus() == 0)	{
				loadNextLevel = true;
				System.out.println("A carregar o próximo nível!");
			}
		
			lastTickProcessedRoom++;
			}
			
			public void moveManel(Direction d) {
				if(canMove(manel,d)){
					manel.move(d);
				}
					
			}
			
			public void moveMovables(){ 
				Iterator<Movable> iterator = objetosMoveis.iterator();
				
				while (iterator.hasNext()) {
					Movable i = iterator.next();
					if(i.getName() == "Banana"){
						Point2D nextPoint = i.getPosition().plus(Direction.DOWN.asVector());
							if(insideMap(nextPoint)){
								i.move(Direction.DOWN);
							}	else	{
								System.out.println("A proxima direcao esta fora do mapa");
								deleteObject(i.getPosition());
								iterator.remove();
								objetosInteractable.remove(i);
							}
					} else {
						i.move(randomPossibleDirection(i));
					}
				}
			}
	
			
			public void processTimables(){
				for(Timable i : objetosTimable){
					i.processTick();
				}
			}
			
			public void interact() {
				Iterator<Interactable> iterator = objetosInteractable.iterator();
				
				while (iterator.hasNext()) {
					Interactable i = iterator.next();
					if (canInteract(i)) {
						i.interact(manel);
						System.out.println("A interagir com objeto: " + i.getName());
			
						if(i.isDeletable())	{  
							deleteObject(i.getPosition());
							iterator.remove();
							objetosMoveis.remove(i);
							System.out.println("Apagando " + i.getName());		
						}
					}
				}			
			}
				
			public void attack(){
				if(randomDonkeyIndex()!= -1){
					Banana banana = new Banana(objetosInteractable.get(randomDonkeyIndex()).getPosition());
					ImageGUI.getInstance().addImage(banana);
					objetosMoveis.add(banana);
					objetosInteractable.add(banana);
				}			
			}
			
			public int randomDonkeyIndex(){//dá um indice aleatório de um Donkey Kong na lista dos Interactable, retorna -1 se não houver gorilas no mapa
				List<Integer> indexes = new ArrayList<>();
				for(int i = 0; i != objetosInteractable.size(); i++){
					if(objetosInteractable.get(i).getName()=="DonkeyKong"){
						indexes.add(i);
					}
				}
		
				if(indexes.size()==0){
					return -1;
				}
	
				System.out.println("tamanho array"+ indexes.size());
				return indexes.get((int)(Math.random()*indexes.size()));		
			}
			
			public boolean canInteract(Interactable i) {
				Point2D manelPosition = manel.getPosition();
				Point2D objectPosition = i.getPosition();
					
				if (isTrap(objectPosition)) {
					Point2D manelGround = manel.getPosition().plus(new Vector2D(0, 1));
			
					if (manelGround.equals(objectPosition)) {
						System.out.println("Armadilha ativada!");
						return true;
					}
				}
				return manelPosition.equals(objectPosition);
			}
			
			public void deleteObject(Point2D posAt) {
				ImageTile tile = findObjectByPoint(posAt);
				ImageGUI.getInstance().removeImage(tile); 	
			}
				
			public Direction randomPossibleDirection(Movable ob){
				Direction d = Direction.random();
				while(!canMove(ob,d)){
					d = Direction.random();
				}
				return d;
			}
			
			public void readRoomFile(String f) {	
				String levelPath = "rooms/" + f;
				File file = new File(levelPath);		
				System.out.println("A ler ficheiro: " + levelPath);
				
				try (Scanner sc = new Scanner(file)) { 
					if(levelNum != 2){
						level = sc.nextLine(); 
					}

					String letras = "";
	
					while (sc.hasNextLine()) {
						letras += sc.nextLine();
					}
					
					matrixRoom = stringToMatrix(letras); 
					loadMap(); 
					sc.close();
				} catch (FileNotFoundException e) {
					System.err.println("Ficheiro não encontrado! " + levelPath);
				}
				
			}
		
			public static void addFloor(){
				for(int i = 0; i != 10; i++){
					for(int j = 0; j != 10; j++){
						ImageGUI.getInstance().addImage(new Floor(new Point2D(i,j)));
					}
				}
			}
		
			public boolean canMove(Movable ob, Direction d){
				Point2D nextPosition = ob.getPosition().plus(d.asVector());
				if(insideMap(nextPosition) && !isBlock(nextPosition)){
					if(d.name()=="UP" && !isStairs(ob.getPosition())){//Caso o objeto nao esteja numa escada não pode subir de posição ou seja "voar"!
						return false;
					}
					return true;
				}
				
				return false;
			}
		
			public boolean isBlock(Point2D p){
				char c = matrixRoom[p.getY()][p.getX()];
				if(c== 'W'||c== 't'){
					return true;
				}
				return false;
			}
			public boolean isStairs(Point2D p){
				char c = matrixRoom[p.getY()][p.getX()];
				if(c == 'S'){
					return true;
				}
				return false;
			}
		
			public boolean isTrap(Point2D p){
				char c = matrixRoom[p.getY()][p.getX()];
				if(c == 't'){
					return true;
				}
				return false;
			}
		
			public boolean isDoor(Point2D p) {
				char c = matrixRoom[p.getY()][p.getX()];
				if(c == '0') {
					return true;
				}
				return false;
			}
		
			public void manelFall(){//Quando nao tem nada por baixo o Manel cai
				Point2D manelGround = manel.getPosition().plus(new Vector2D(0, 1));
				if(!isBlock(manelGround) && !isStairs(manelGround)){
					moveManel(Direction.DOWN);
				}
			}
		
			public boolean insideMap(Point2D p){
				if(p.getX()==-1||p.getX()==10||p.getY()==-1||p.getY()==10){
					return false;
				}
				return true;
			}
		
			public static char[][] stringToMatrix(String s){
				char[][] matr = new char[10][10];
				for(int i=0; i!= s.length();i++){
					matr[i/10][i%10]=s.charAt(i);
					System.out.println("indice ["+ i/10+"]"+ "["+i%10+"]"+ " é "+s.charAt(i) );
				}
				return matr;
			}
		
		
		public static void loadMap(){
			Interactable interactable;
		
			for(int i = 0; i != 10;i++){
				for(int j = 0; j != 10; j++){
					switch (matrixRoom[i][j]) {
						case 'W':			
							Wall wall = new Wall(new Point2D(j,i));
							ImageGUI.getInstance().addImage(wall);
							objetosFixos.add(wall);
							break;
								
						case 'S':
							Stairs stairs = new Stairs(new Point2D(j,i));
							objetosFixos.add(stairs);
							ImageGUI.getInstance().addImage(stairs);
							break;

						case 'H':
							heroStartingPosition= new Point2D(j,i);

							if(levelNum == 0){
								manel = new Manel(heroStartingPosition);
							}	

							ImageGUI.getInstance().addImage(manel);
							break;	
						
						case '0':
							doorPosition = new Point2D(j,i);
							Door door = new Door(doorPosition);
							objetosInteractable.add(door);
							currentDoor = door;
							ImageGUI.getInstance().addImage(door);  
							break;	

						case 't':
							Trap trap = new Trap(new Point2D(j,i));
							objetosInteractable.add(trap);
							ImageGUI.getInstance().addImage(trap);
							break;
					
						case 'G':
							DonkeyKong donkeyKong = new DonkeyKong(new Point2D(j,i));
							ImageGUI.getInstance().addImage(donkeyKong);
							objetosMoveis.add(donkeyKong);
							objetosInteractable.add(donkeyKong);
							break;
						
						case 's':
							interactable = new Sword(new Point2D(j,i));
							ImageGUI.getInstance().addImage(interactable);
							objetosInteractable.add(interactable);
							break; 
					 
						case 'm':
							Bife bife = new Bife(new Point2D(j,i));	
							ImageGUI.getInstance().addImage(bife); 
							objetosInteractable.add(bife);  
							objetosTimable.add(bife);  
							break;
							
						case 'F':
							Flag flag  = new Flag(new Point2D(j,i));
							ImageGUI.getInstance().addImage(flag); 
							objetosInteractable.add(flag);
							
						default:
							break;
					}
				}
			}	
		}
	
	public ImageTile findObjectByPoint(Point2D p) {
		for (ImageTile i : objetosInteractable) {
			if (i.getPosition().equals(p)) {
				return i;
			}
		}
	
		System.err.println("Objeto não encontrado na posição " + p);
		return null;
	}

	public boolean getLoadNextLevel(){
		return loadNextLevel;
	}

	public void respawnManel(Point2D startingPosition, boolean killed){
		Manel deadManel = manel;
		ImageGUI.getInstance().removeImage(deadManel);
		int lives = 0;
		int damageLevel = 0;
		int health = 0;

		if(killed){
			lives = deadManel.getLives() - 1;
			health = 100;
			damageLevel = 25;

		} else {
			lives = deadManel.getLives();
			damageLevel = deadManel.getDamage();
			health = deadManel.getHealth();
		}
		
		manel = new Manel(heroStartingPosition);
		manel.setLives(lives);
		manel.setDamageLevel(damageLevel);
		manel.setHealth(health);

		ImageGUI.getInstance().addImage(manel);
	}
	
	public static void teleportManel(Point2D point) {
		Manel newManel = new Manel(point);
		newManel.setLives(manel.getLives());
		newManel.setDamageLevel(manel.getDamage());
		newManel.setHealth(manel.getHealth());
		
		ImageGUI.getInstance().removeImage(manel);
		
		manel = newManel;
		ImageGUI.getInstance().addImage(newManel);
	}

	public void clearPreviousLevel(){	
		clearMovables();
		clearInteractables();
		clearMap();
	}

	public void clearMovables(){
		Iterator<Movable> iterator = objetosMoveis.iterator();

		while(iterator.hasNext()) {
			Movable i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void clearInteractables(){
		Iterator<Interactable> iterator = objetosInteractable.iterator();

		while(iterator.hasNext()) {
			Interactable i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void clearMap(){
		Iterator<GameObject> iterator = objetosFixos.iterator();

		while(iterator.hasNext()){
			GameObject i = iterator.next();
			ImageGUI.getInstance().removeImage(i);
		}
	}

	public void manelStatus() {	
		if(manel.getHealth() < 0){
			respawnManel(heroStartingPosition, true);	
		}
		ImageGUI.getInstance().setStatusMessage("Vidas: " + manel.getLives() + "  Saúde: " + manel.getHealth() +  "  Dano: " + manel.getDamage());
	}
}